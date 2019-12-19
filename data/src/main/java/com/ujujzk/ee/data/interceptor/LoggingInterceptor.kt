package com.ujujzk.ee.data.interceptor

import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.internal.http.promisesBody
import okhttp3.internal.platform.Platform
import okio.Buffer
import okio.GzipSource
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.EOFException
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.text.Charsets.UTF_8

/**
 * Created by ujujzk on 12.04.2018
 * ujujzk@gmail.com
 * <p>
 * Based on {@linkplain okhttp3.logging.HttpLoggingInterceptor}
 */
class LoggingInterceptor(
    private val level: Level = Level.NONE,
    val logger: (String) -> Unit = { message -> Platform.get().log(Platform.INFO, message, null) },
    private val inlineBody: Boolean = false
): Interceptor {

    companion object {
        const val JSON_INDENT = 2
    }

    enum class Level { NONE, BASIC, HEADERS, BODY, HEADERS_BODY }

    @Volatile private var headersToRedact = emptySet<String>()

    fun redactHeader(name: String) {
        val newHeadersToRedact = TreeSet(String.CASE_INSENSITIVE_ORDER)
        newHeadersToRedact += headersToRedact
        newHeadersToRedact += name
        headersToRedact = newHeadersToRedact
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        if (level == Level.NONE) {
            return chain.proceed(request)
        }

        val message = StringBuilder()

        val logBody = level == Level.HEADERS_BODY || level == Level.BODY
        val logHeaders = level == Level.HEADERS_BODY || level == Level.HEADERS

        val requestBody = request.body

        message.append("--> ${request.method} ${request.url}")
            .append(chain.connection()?.protocol() ?: "")
        if (!logHeaders && requestBody != null) {
            message.append(" (${requestBody.contentLength()}-byte body)")
        }
        message.append("\n")

        if (logHeaders) {
            if (requestBody != null) {
                if (requestBody.contentType() != null) {
                    message.append("Content-Type: ${requestBody.contentType()}\n")
                }
                if (requestBody.contentLength() != -1L) {
                    message.append("Content-Length: ${requestBody.contentLength()}\n")
                }
            }

            val headers = request.headers
            var headerName: String
            for (i in 0 until headers.size) {
                headerName = headers.name(i)
                if (!headerName.equals("Content-Type", true) &&
                    !headerName.equals("Content-Length", true)) {

                    val value = if (headers.name(i) in headersToRedact) "██████" else headers.value(i)
                    message.append("$headerName: ${value}\n")
                }
            }
        }
        if (!logBody || requestBody == null) {
            message.append("--> END ${request.method}\n")
        } else if (bodyEncoded(request.headers)) {
            message.append("--> END ${request.method} encoded body omitted)\n")
        } else {
            val buffer = Buffer()
            requestBody.writeTo(buffer)

            val charset = requestBody.contentType()?.charset(UTF_8) ?: UTF_8

            message.append('\n')
            if (isProbablyUtf8(buffer)) {
                val body = if (inlineBody) buffer.clone().readString(charset) else formatJson(
                    buffer.clone().readString(charset)
                )
                message.append("$body\n--> END ${request.method} (${requestBody.contentLength()}-byte body)\n")
            } else {
                message.append("--> END ${request.method} (binary ${requestBody.contentLength()} -byte body omitted)\n")
            }
        }

        logger(message.toString())
        message.setLength(0)

        val startNs = System.nanoTime()
        val response = try {
            chain.proceed(request)
        } catch (ex: IOException) {
            message.append("<-- HTTP FAILED: \n$ex\n")
            logger(message.toString())
            throw ex
        }

        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)

        val responseBody = response.body
        val contentLength = responseBody?.contentLength() ?: -1L
        val bodySize = if (contentLength != -1L) "$contentLength-byte" else "unknown-length"
        message.append("<-- ${response.code} ${response.message} ${response.request.url} ($tookMs ms, $bodySize body)\n")

        val headers = response.headers
        if (logHeaders) {
            for (i in 0 until headers.size) {
                val value = if (headers.name(i) in headersToRedact) "██████" else headers.value(i)
                message.append("${headers.name(i)}: ${value}\n")
            }
        }

        if (!logBody || !response.promisesBody()) {
            message.append("<-- END HTTP\n")
        } else if (bodyEncoded(response.headers)) {
            message.append("<-- END HTTP (encoded body omitted)\n")
        } else if (responseBody == null) {
            message.append("<-- END HTTP (body = null)\n")
        } else {
            val source = responseBody.source()
            source.request(Long.MAX_VALUE)
            var buffer = source.buffer


            var gzippedLength: Long? = null
            if ("gzip".equals(headers["Content-Encoding"], ignoreCase = true)) {
                gzippedLength = buffer.size
                GzipSource(buffer.clone()).use { gzippedResponseBody ->
                    buffer = Buffer()
                    buffer.writeAll(gzippedResponseBody)
                }
            }

            val charset = responseBody.contentType()?.charset(UTF_8) ?: UTF_8

            if (!isProbablyUtf8(buffer)) {
                message.append("\n<-- END HTTP (binary ${buffer.size}-byte body omitted)\n")
                logger(message.toString())
                return response
            }

            if (contentLength > 0L) {
                val body = if (inlineBody) buffer.clone().readString(charset) else formatJson(
                    buffer.clone().readString(charset)
                )
                message.append("$body\n")
            }

            if (gzippedLength != null) {
                message.append("<-- END HTTP (${buffer.size}-byte, $gzippedLength-gzipped-byte body)\n")
            } else {
                message.append("<-- END HTTP (${buffer.size}-byte body)\n")
            }
        }
        logger(message.toString())

        return response
    }


    private fun isProbablyUtf8(buffer: Buffer): Boolean {
        return try {
            val prefix = Buffer()
            val byteCount = buffer.size.coerceAtMost(64)
            buffer.copyTo(prefix, 0, byteCount)
            for (i in 0 until 16) {
                if (prefix.exhausted()) {
                    break
                }
                val codePoint = prefix.readUtf8CodePoint()
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false
                }
            }
            true
        } catch (_: EOFException) {
            false // Truncated UTF-8 sequence.
        }
    }

    private fun bodyEncoded(headers: Headers): Boolean {
        val contentEncoding = headers["Content-Encoding"] ?: return false
        return !contentEncoding.equals("identity", ignoreCase = true) &&
                !contentEncoding.equals("gzip", ignoreCase = true)
    }

    private fun formatJson(jsonC: String?): String {
        var json = jsonC
        if (json == null || json.isEmpty()) {
            return "Empty/Null json content"
        }
        try {
            json = json.trim()
            if (json.startsWith("{")) {
                val jsonObject = JSONObject(json)
                return jsonObject.toString(JSON_INDENT)
            }
            if (json.startsWith("[")) {
                val jsonArray = JSONArray(json)
                return jsonArray.toString(JSON_INDENT)
            }
            return json
        } catch (e: JSONException) {
            return json
        }
    }


}