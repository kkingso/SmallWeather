package com.kkw.smallweather.http

import com.kkw.smallweather.api.WeatherService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

/**
 * 构建retrofit
 */
object RetrofitBuilder {

    private const val WEATHER_URL = "https://devapi.qweather.com"

    /**
     * 构建Retrofit
     */
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(WEATHER_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /**
     * 创建Api服务
     */
    fun <T> create(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }
}
