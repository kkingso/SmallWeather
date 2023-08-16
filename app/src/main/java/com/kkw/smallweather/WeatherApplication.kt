package com.kkw.smallweather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * app
 */
class WeatherApplication: Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        // 和风天气token密钥
        const val KEY_ID = "b17fe2ea87ad43e9a2e6a2a1120171cd"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}