package com.sourav.oversplash

import android.app.Application
import android.content.Context

class Oversplash: Application() {
    init {
        instance = this
    }
    companion object{
        private var instance: Oversplash? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        val context: Context = applicationContext();
    }
}