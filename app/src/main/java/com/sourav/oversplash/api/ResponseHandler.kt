package com.sourav.oversplash.api

interface ResponseHandler<T> {
    fun onSuccess(data: T)
    fun onFailure(throwable: Throwable)
    fun onError(httpCode: Int)
}