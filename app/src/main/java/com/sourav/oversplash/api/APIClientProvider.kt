package com.sourav.oversplash.api

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class APIClientProvider: APICaller {
    private val baseURL = "https://api.unsplash.com/"

//    fun get(clazz: Class<T>): T{
//         val retrofit = Retrofit.Builder()
//            .baseUrl(baseURL)
//            .client(getOkHttpClient())
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        return retrofit.create(UnplashAPIService::class.java)
//    }

    private fun getOkHttpClient(): OkHttpClient{
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(AuthInterceptor())
            .build()
    }

    public override fun <T> doAPICall(result: Call<T>, responseHandler: ResponseHandler<T>) {
        result.enqueue(RetrofitResponseHandler(responseHandler))
    }

    override fun <T> getAPIClient(apiService: Class<T>):T {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(apiService)
    }

}