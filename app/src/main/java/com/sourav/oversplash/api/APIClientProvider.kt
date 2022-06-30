package com.sourav.oversplash.api

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class APIClientProvider: APICaller {

    companion object{
        val baseURL = "https://api.unsplash.com/"

        @JvmStatic
        fun get(): Retrofit{
            return Retrofit.Builder()
                .baseUrl(baseURL)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        }

        private fun getOkHttpClient(): OkHttpClient{
            return OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(AuthInterceptor())
                .build()
        }

    }

    override fun <T> doAPICall(result: Call<T>, responseHandler: ResponseHandler<T>) {
        result.enqueue(RetrofitResponseHandler(responseHandler))
    }

}