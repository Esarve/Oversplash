package com.sourav.oversplash.utils

class DataWrapper<T> {
    val status: Status
    val data: T?
    val message: String?
    var errorCode = 0

    constructor(status: Status, data: T?, message: String?) {
        this.status = status
        this.data = data
        this.message = message
    }

    constructor(status: Status, data: T?, message: String?, errorCode: Int) {
        this.status = status
        this.data = data
        this.message = message
        this.errorCode = errorCode
    }

    enum class Status {
        SUCCESS, ERROR, LOADING, FAILURE
    }

    companion object {
        fun <T> success(data: T): DataWrapper<T> {
            return DataWrapper(Status.SUCCESS, data, Status.SUCCESS.name)
        }

        fun <T> error(msg: String, data: T?): DataWrapper<T> {
            return DataWrapper(Status.ERROR, data, msg)
        }

        fun <T> errorWithCode(msg: String, data: T?, errorCode: Int): DataWrapper<T> {
            return DataWrapper(Status.ERROR, data, msg, errorCode)
        }

        fun <T> loading(data: T?): DataWrapper<T> {
            return DataWrapper(Status.LOADING, data, Status.LOADING.name)
        }

        fun <T> failure(data: T?): DataWrapper<T> {
            return DataWrapper(Status.FAILURE, data, Status.FAILURE.name)
        }
    }
}