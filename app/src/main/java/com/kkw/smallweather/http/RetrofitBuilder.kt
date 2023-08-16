package com.kkw.smallweather.http

import com.kkw.smallweather.call.adapter.LiveDataCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

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
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addCallAdapterFactory(LiveDataCallAdapterFactory.create())
        .build()

    /**
     * 创建Api服务
     */
    fun <T> create(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }
}
