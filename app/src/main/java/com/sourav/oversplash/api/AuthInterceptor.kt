package com.sourav.oversplash.api

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val clientId = "QQKko1tMpPGcKo1eRD3rbW2BCfffR4GnEiZauTDtnUU"
        val authenticatedRequest = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Client-ID $clientId")
            .build()
        return chain.proceed(authenticatedRequest)
    }
}