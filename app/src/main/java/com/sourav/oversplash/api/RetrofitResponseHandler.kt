package com.sourav.oversplash.api

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitResponseHandler<T>(val handler: ResponseHandler<T>): Callback<T> {


    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful){
            response.body()?.let { handler.onSuccess(it) };
        }else{
            handler.onError()
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        handler.onError()
    }
}