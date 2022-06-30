package com.sourav.oversplash.api

interface ResponseHandler<T> {
    fun onSuccess(data: T)
    fun onFailure()
    fun onError()
}