package com.kkw.smallweather.bean

import com.google.gson.annotations.SerializedName

/**
 *
 */
data class WeatherNowBean(
    // API状态码
    @SerializedName("code")
    val code: Int?,
    // 最近更新时间
    @SerializedName("updateTime")
    val updateTime: String?,
    // 当前数据的响应式页面，便于嵌入网站或应用
    @SerializedName("fxLink")
    val fxLink: String?,
    // 实时天气
    @SerializedName("now")
    val now: NowBean?
)
