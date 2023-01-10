package com.linc.network.utils

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val token: String,
    private val isBearer: Boolean
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.request()
            .newBuilder()
            .addHeader(
                "Authorization",
                "${if(isBearer) "Bearer " else ""}$token"
            )
            .build()
            .let(chain::proceed)
    }
}