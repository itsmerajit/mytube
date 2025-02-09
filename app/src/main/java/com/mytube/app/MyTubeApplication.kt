package com.mytube.app

import android.app.Application
import timber.log.Timber

class MyTubeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize Timber logging
        if (isDebug()) {
            Timber.plant(Timber.DebugTree())
        }
        Timber.d("MyTube Application Initialized")
    }

    private fun isDebug(): Boolean {
        return true  // You can modify this logic later
    }
}
