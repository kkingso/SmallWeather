package com.kkw.smallweather.bean

/**
 * 逐天天气
 */
data class DailyBean(
    // 预报日期
    val fxDate: String?,
    // 日出时间，在高纬度地区可能为空
    val sunrise: String?,
    // 日落时间，在高纬度地区可能为空
    val sunset: String?,
    // 当天月升时间，可能为空
    val moonrise: String?,
    // 当天月落时间，可能为空
    val moonset: String?,
    // 月相名称
    val moonPhase: String?,
    // 月相图标代码
    val moonPhaseIcon: String?,
    // 预报当天最高温度
    val tempMax: String?,
    // 预报当天最低温度
    val tempMin: String?,
    // 预报白天天气状况的图标代码
    val iconDay: String?,
    // 预报白天天气状况文字描述
    val textDay: String?,
    // 预报夜间天气状况的图标代码
    val iconNight: String?,
    // 预报晚间天气状况文字描述
    val textNight: String?,
    // 预报白天风向360角度
    val wind360Day: String?,
    // 预报白天风向
    val windDirDay: String?,
    // 预报白天风力等级
    val windScaleDay: String?,
    // 预报白天风速，公里/小时
    val windSpeedDay: String?,
    // 预报夜间风向360角度
    val wind360Night: String?,
    // 预报夜间当天风向
    val windDirNight: String?,
    // 预报夜间风力等级
    val windScaleNight: String?,
    // 预报夜间风速，公里/小时
    val windSpeedNight: String?,
    // 预报当天总降水量，默认单位：毫米
    val precip: String?,
    // 紫外线强度指数
    val uvIndex: String?,
    // 相对湿度，百分比数值
    val humidity: String?,
    // 大气压强，默认单位：百帕
    val pressure: String?,
    // 能见度，默认单位：公里
    val vis: String?,
    // 云量，百分比数值。可能为空
    val cloud: String?,
    // 原始数据来源，或数据源说明，可能为空
    val sources: String?,
    // 数据许可或版权声明，可能为空
    val license: String?
)
