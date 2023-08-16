package com.kkw.smallweather.call.adapter

import androidx.lifecycle.LiveData
import com.kkw.smallweather.bean.BaseHttpException
import com.kkw.smallweather.bean.BaseWeather
import com.kkw.smallweather.bean.ServerCodeNoSuccessException
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

class LiveDataCallAdapter<R>(private val responseType: Type): CallAdapter<R, LiveData<R>> {

    override fun responseType(): Type {
        return responseType
    }

    override fun adapt(call: Call<R>): LiveData<R> {
        return object : LiveData<R>() {

            private val started = AtomicBoolean(false)

            override fun onActive() {
                // 避免重复请求
                if (started.compareAndSet(false, true)) {
                    call.enqueue(object : Callback<R> {
                        override fun onResponse(call: Call<R>, response: Response<R>) {
                            val body = response.body() as BaseWeather<*>
                            if (body.isSuccess) {
                                // 成功状态，直接返回 body
                                postValue(response.body())
                            } else {
                                // 失败状态，返回格式化好的 BaseWeather 对象
                                postValue(BaseHttpException.error(ServerCodeNoSuccessException(body)) as R)
                            }
                        }

                        override fun onFailure(call: Call<R>, throwable: Throwable) {
                            // 网络请求失败，根据 throwable 类型来构建 BaseWeather
                            postValue(BaseHttpException.error(throwable) as R)
                        }
                    })
                }

                super.onActive()
            }
        }
    }
}