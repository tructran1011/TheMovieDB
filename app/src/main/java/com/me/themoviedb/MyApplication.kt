package com.me.themoviedb

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        setupTimber()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {

                override fun createStackElementTag(element: StackTraceElement): String {
                    return "coin_(${element.fileName}:${element.lineNumber})"
                }
            })
        }
    }
}