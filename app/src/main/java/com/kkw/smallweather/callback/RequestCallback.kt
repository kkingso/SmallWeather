package com.kkw.smallweather.callback

import com.kkw.smallweather.bean.BaseWeather

/**
 * 数据请求状态
 */
interface RequestCallback<T> {

    /**
     * 成功
     */
    fun onSuccess(data: BaseWeather<T>?)

    /**
     * 错误
     */
    fun onError(errorCode: Int, data: BaseWeather<T>?) {}

    /**
     * 失败
     */
    fun onFailure(t: Throwable)
}