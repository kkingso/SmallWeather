package com.kkw.smallweather.bean

import com.google.gson.annotations.SerializedName

/**
 * 天气请求的基础数据
 */
data class BaseWeather<T>(
    // API状态码
    @SerializedName("code")
    val errorCode: Int?,
    // API错误信息
    private val errorMessage: String?,
    // 最近更新时间
    @SerializedName("updateTime")
    val updateTime: String?,
    // 当前数据的响应式页面，便于嵌入网站或应用
    @SerializedName("fxLink")
    val fxLink: String?,
    // 实时天气[现在，每日，每小时]
    @SerializedName(value = "now", alternate = ["daily", "hourly"])
    val bean: T?
) {

    // api 请求是否成功
    val isSuccess: Boolean
        get() = errorCode == 200
}
