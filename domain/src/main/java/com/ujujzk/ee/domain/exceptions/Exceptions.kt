package com.ujujzk.ee.domain.exceptions

import java.io.IOException

/**
 * Parent on Base exception must be IOException cause OkHttp doesn't send other Exceptions over thread boundaries
 * https://stackoverflow.com/questions/58697459/handle-exceptions-thrown-by-a-custom-okhttp-interceptor-in-kotlin-coroutines
 * https://github.com/square/okhttp/pull/5457
 */
abstract class HandledException(cause: Throwable? = null, message: String? = null): IOException(message, cause)

class InvalidFullNameException: HandledException(message = "InvalidFullNameException")

class TokenExpiredException: HandledException(message = "TokenExpiredException")

class DataError(cause: Throwable?): HandledException(cause)

class NetworkError(cause: Throwable?): HandledException(cause)
class ParseException(cause: Throwable?): HandledException(cause)


sealed class StoreError(message: String?) : HandledException(message = message){
    class NoUserException(message: String?) : StoreError(message)
}


sealed class ServerError(message: String?) : HandledException(message = message) {
    class UnknownError(cause: Throwable?) : ServerError(cause?.message)
    class SystemError(cause: Throwable?) : ServerError(cause?.message)

//    class BadRequestError(error: DomainError?) : ServerError(error)
//    class AuthError(error: DomainError?) : ServerError(error)
//    class Validation(error: DomainError?) : ServerError(error)
//    class NotFoundError(error: DomainError?) : ServerError(error)
//    class BadRequest(error: DomainError?) : ServerError(error)
//    class ConflictError(error: DomainError?) : ServerError(error)
//    class UnsupportedMediaError(error: DomainError?) : ServerError(error)
}




