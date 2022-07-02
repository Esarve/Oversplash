package com.sourav.oversplash

import android.app.Application
import timber.log.Timber
import timber.log.Timber.Forest.plant


class Oversplash: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        if (BuildConfig.DEBUG) plant(Timber.DebugTree())
    }

    companion object {
        lateinit var instance: Oversplash
            private set
    }
}