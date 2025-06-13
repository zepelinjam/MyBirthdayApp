package com.yurcha.mybirthdayapp.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyBirthdayApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
