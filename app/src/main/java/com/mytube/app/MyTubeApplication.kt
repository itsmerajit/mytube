package com.mytube.app

import android.app.Application
import android.content.pm.ApplicationInfo
import android.os.Build
import android.os.StrictMode
import android.util.Log
import androidx.annotation.RequiresApi
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import java.util.concurrent.Executors

@HiltAndroidApp
class MyTubeApplication : Application() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        
        // Initialize logging early
        initializeLogging()
        
        // Log application startup details
        logApplicationDetails()
        
        // Setup thread policy for debugging
        setupStrictMode()
        
        Timber.d("MyTube Application Initialized")
    }

    private fun initializeLogging() {
        if (isDebugBuild()) {
            Timber.plant(object : Timber.DebugTree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    // Custom logging with additional context
                    val threadName = Thread.currentThread().name
                    val processId = android.os.Process.myPid()
                    val threadId = Thread.currentThread().id
                    
                    val enhancedMessage = "[$threadName:$processId:$threadId] $message"
                    
                    when (priority) {
                        Log.DEBUG -> super.log(priority, tag, enhancedMessage, t)
                        Log.ERROR -> super.log(priority, tag, " ERROR: $enhancedMessage", t)
                        Log.WARN -> super.log(priority, tag, " WARNING: $enhancedMessage", t)
                        else -> super.log(priority, tag, enhancedMessage, t)
                    }
                }
            })
        }
        
        Timber.d("MyTubeApplication: Logging initialized")
    }

    private fun logApplicationDetails() {
        try {
            val packageManager = packageManager
            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            val appInfo = packageManager.getApplicationInfo(packageName, 0)
            
            Timber.d("Application Details:")
            Timber.d("Package Name: $packageName")
            Timber.d("Version Name: ${packageInfo.versionName}")
            Timber.d("Version Code: ${packageInfo.longVersionCode}")
            Timber.d("Target SDK: ${appInfo.targetSdkVersion}")
            Timber.d("Min SDK: ${Build.VERSION.SDK_INT}")
            Timber.d("Device Model: ${Build.MODEL}")
            Timber.d("Device Manufacturer: ${Build.MANUFACTURER}")
        } catch (e: Exception) {
            Timber.e(e, "Error logging application details")
        }
    }

    private fun setupStrictMode() {
        if (isDebugBuild()) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyLog()
                    .build()
            )
            
            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build()
            )
            
            Timber.d("StrictMode configured for enhanced debugging")
        }
    }

    private fun isDebugBuild(): Boolean {
        return try {
            val appInfo = applicationInfo
            (appInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0
        } catch (e: Exception) {
            Timber.e(e, "Error checking debug build")
            false
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        Timber.d("MyTubeApplication: Application terminated")
    }
}
