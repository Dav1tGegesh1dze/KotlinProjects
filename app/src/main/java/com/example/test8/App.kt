package com.example.test8

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import android.content.Context

@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object{
        var context: Context? = null
    }
}