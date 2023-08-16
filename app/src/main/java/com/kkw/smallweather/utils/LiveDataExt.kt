package com.kkw.smallweather.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.kkw.smallweather.bean.BaseWeather

fun <T>LiveData<T>.observeState(context: LifecycleOwner,onSuccess: T.()-> Unit,onError: T.()-> Unit) {
    this.observe(context) {
        val data = it as BaseWeather<*>
        if (data.isSuccess) {
            onSuccess.invoke(it)
        } else {
            onError.invoke(it)
        }
    }
}