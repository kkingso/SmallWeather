package com.kkw.smallweather.api

import androidx.lifecycle.LiveData
import com.kkw.smallweather.WeatherApplication.Companion.KEY_ID
import com.kkw.smallweather.bean.BaseWeather
import com.kkw.smallweather.bean.DailyBean
import com.kkw.smallweather.bean.HourlyBean
import com.kkw.smallweather.bean.NowBean
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * 天气service api
 */
interface WeatherService {

    @GET("/v7/weather/now?key=${KEY_ID}")
    fun getWeatherNow(@Query("location") location: String?): Call<BaseWeather<NowBean>>

    @GET("v7/weather/{days}d?key=${KEY_ID}")
    fun getWeatherDaily(@Path("days") days: Int, @Query("location") location: String?): Call<BaseWeather<MutableList<DailyBean>>>

    @GET("v7/weather/{hours}h?key=${KEY_ID}")
    fun getWeatherHourly(@Path("hours") hours: Int, @Query("location") location: String?): Call<BaseWeather<MutableList<HourlyBean>>>

    @GET("/v7/weather/now?key=${KEY_ID}")
    fun getWeatherNow2(@Query("location") location: String?): Observable<BaseWeather<NowBean>>

    @GET("/v7/weather/now?key=${KEY_ID}")
    fun getWeatherNow3(@Query("location") location: String?): LiveData<BaseWeather<NowBean>>
}