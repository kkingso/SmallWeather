package com.kkw.smallweather.http

import android.util.Log
import com.kkw.smallweather.api.WeatherService
import com.kkw.smallweather.bean.BaseWeather
import com.kkw.smallweather.callback.RequestCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * 网络请求的仓库
 */
object HttpRepository {

    private const val TAG = "HttpRepository"

    val weatherService = RetrofitBuilder.create(WeatherService::class.java)

    fun <T> Call<BaseWeather<T>>.await(callback: RequestCallback<T>) {
        enqueue(object : Callback<BaseWeather<T>> {
            override fun onResponse(
                call: Call<BaseWeather<T>>,
                response: Response<BaseWeather<T>>
            ) {
                response.let {
                    Log.i(TAG, "onResponse-->${it.raw()}")
                    Log.i(TAG, "onSuccess: ${it.body()}")
                    if (it.code() == 200) {
                        callback.onSuccess(it.body())
                    } else {
                        callback.onError(it.code(), it.body())
                    }
                }
            }

            override fun onFailure(call: Call<BaseWeather<T>>, t: Throwable) {
                Log.e(TAG, "onFailure-->${call.request()}", t)
                callback.onFailure(t)
            }
        })
    }
}