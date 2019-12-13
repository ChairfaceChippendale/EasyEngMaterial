package com.ujujzk.ee.data.interceptor

import okhttp3.*

class MockInterceptor(
    val isDebugBuild: Boolean
) : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        if (isDebugBuild) {
            val uri = chain.request().url().uri().toString()
            val responseString = when {
                uri.endsWith("voc") -> getListOfReposBeingStarredJson2
                else -> ""
            }

            return Response.Builder()
                .request(chain.request())
                .code(200)
                .protocol(Protocol.HTTP_2)
                .message("Mock message")
                .body(
                    ResponseBody.create(
                        MediaType.parse("application/json"),
                        responseString.toByteArray()
                    )
                )
                .addHeader("content-type", "application/json")
                .addHeader("header-one", "value-one")
                .addHeader("header-two", "value-two")
                .addHeader("header-three", "value-three")
                .build()
        } else {
            //just to be on safe side.
            throw IllegalAccessError(
                "MockInterceptor is only meant for Testing Purposes and " +
                        "bound to be used only with DEBUG mode"
            )
        }
    }


    private val getListOfReposBeingStarredJson = """
{
    "packs" : [
        {
            "title": "first",
            "cards": [
                {
                    "question": "q1",
                    "answer": "a1"
                },{
                    "question": "q2",
                    "answer": "a2"
                },{
                    "question": "q3",
                    "answer": "a3"
                }
            ]
        },{
            "title": "second",
            "cards": [
                {
                    "question": "q4",
                    "answer": "a4"
                },{
                    "question": "q5",
                    "answer": "a5"
                },{
                    "question": "q6",
                    "answer": "a6"
                }
            ]
        },{
            "title": "third",
            "cards": [
                {
                    "question": "q7",
                    "answer": "a7"
                },{
                    "question": "q8",
                    "answer": "a8"
                },{
                    "question": "q9",
                    "answer": "a9"
                },{
                    "question": "q10",
                    "answer": "a10"
                }
            ]
        }
    
    ]
}
"""

    private val getListOfReposBeingStarredJson2 = """{ "packs": [{"title": "first", "cards":[{"question": "q1", "answer": "a1"},{"question": "q2", "answer": "a2"},{"question": "q3", "answer": "a3"}]},{"title": "second", "cards":[{"question": "q4", "answer": "a4"},{"question": "q5", "answer": "a5"},{"question": "q6", "answer": "a6"}]},{"title": "third", "cards": [{"question": "q7", "answer": "a7"},{"question": "q8", "answer": "a8"},{"question": "q9", "answer": "a9"},{"question": "q10", "answer": "a10"}]}]}"""
}