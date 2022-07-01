package com.sourav.oversplash

import android.app.Application

class Oversplash: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: Oversplash
            private set
    }
}