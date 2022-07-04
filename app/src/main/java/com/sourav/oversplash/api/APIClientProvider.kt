package com.sourav.oversplash.api

import com.sourav.oversplash.Oversplash
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
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

        val httpCacheDirectory = File(Oversplash.instance.cacheDir, "responses")
        val cacheSize = 10 * 1024 * 1024 // 10 MiB
        val cache = Cache(httpCacheDirectory, cacheSize.toLong())

        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .cache(cache)
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