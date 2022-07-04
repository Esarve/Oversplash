package com.sourav.oversplash.api

import com.sourav.oversplash.BuildConfig
import com.sourav.oversplash.Oversplash
import com.sourav.oversplash.utils.Utils
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val clientId = BuildConfig.ACCESS_KEY
        val authenticatedRequest = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Client-ID $clientId")
            .build()

        val originalResponse = chain.proceed(authenticatedRequest)
        return if (Utils.isNetworkConnected(Oversplash.instance)) {
            val maxAge = 60
            originalResponse.newBuilder()
                .header("Cache-Control", "public, max-age=$maxAge")
                .build()
        } else {
            val maxStale = 60 * 60 * 24 * 28
            originalResponse.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                .build()
        }
    }
}