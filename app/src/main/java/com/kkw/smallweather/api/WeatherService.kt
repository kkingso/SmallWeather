package com.kkw.smallweather.api

import com.kkw.smallweather.bean.BaseWeather
import com.kkw.smallweather.bean.DailyBean
import com.kkw.smallweather.bean.HourlyBean
import com.kkw.smallweather.bean.NowBean
import com.kkw.smallweather.bean.WeatherNowBean
import com.kkw.smallweather.http.HttpRepository
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * 天气service api
 */
interface WeatherService {

    @GET("/v7/weather/now?key=${HttpRepository.KEY_ID}")
    fun getWeatherNow(@Query("location") location: String): Call<BaseWeather<NowBean>>

    @GET("v7/weather/{days}d?key=${HttpRepository.KEY_ID}")
    fun getWeatherDaily(@Path("days") days: Int, @Query("location") location: String): Call<BaseWeather<MutableList<DailyBean>>>

    @GET("v7/weather/{hours}h?key=${HttpRepository.KEY_ID}")
    fun getWeatherHourly(@Path("hours") hours: Int, @Query("location") location: String): Call<BaseWeather<MutableList<HourlyBean>>>
}