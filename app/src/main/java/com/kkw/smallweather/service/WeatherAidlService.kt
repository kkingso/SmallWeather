package com.kkw.smallweather.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.google.gson.Gson
import com.kkw.smallweather.ICallback
import com.kkw.smallweather.IServiceBinder
import com.kkw.smallweather.bean.BaseWeather
import com.kkw.smallweather.bean.NowBean
import com.kkw.smallweather.callback.RequestCallback
import com.kkw.smallweather.http.HttpRepository
import com.kkw.smallweather.http.HttpRepository.await
import org.json.JSONObject


class WeatherAidlService: Service() {

    private var mCallback: ICallback? = null

    override fun onCreate() {
        super.onCreate()
        Log.d("kkkw", "onCreate: ")
    }

    override fun onBind(intent: Intent?): IBinder {
        return stub
    }

    private val stub: IServiceBinder.Stub = object : IServiceBinder.Stub() {

        override fun setCallback(callback: ICallback?) {
            mCallback = callback
        }

        override fun receivedSync(jsonData: String?): String {
            TODO("Not yet implemented")
        }

        override fun receivedAsync(jsonData: String?) {
            val jsonObject = jsonData?.let { JSONObject(it) }
            val jsonData = jsonObject?.optJSONObject("data")

            val cityId = jsonData?.optString("cityId")
            HttpRepository.weatherService.getWeatherNow(cityId)
                .await(object : RequestCallback<NowBean> {
                    override fun onSuccess(data: BaseWeather<NowBean>?) {
                        val response = buildResponse("nowWeather", Gson().toJson(data))
                        mCallback?.send(response)
                        Log.d("kkkw", "onSuccess: ")
                    }

                    override fun onFailure(t: Throwable) {
                        val response = buildResponse("error", t.toString())
                        mCallback?.send(response)
                    }
                })
        }

    }

    private fun buildResponse(key: String, value: String?): String {
        val data = JSONObject()
        data.put(key, value)
        val jsonObject = JSONObject()
        jsonObject.put("data", data)
        return jsonObject.toString()
    }

}