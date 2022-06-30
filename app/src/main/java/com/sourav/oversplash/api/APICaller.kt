package com.sourav.oversplash.api

import retrofit2.Call

interface APICaller {
    fun <T> doAPICall(result: Call<T>, responseHandler: ResponseHandler<T> )
//    fun <T> getAPIClient(apiService: Class<T>, context:Context)
}