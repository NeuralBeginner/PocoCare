package com.neuralbeginner.pococare

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PocoCareApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}