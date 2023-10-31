package com.kkw.smallweather.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object CommonUtil {

    /**
     * 时间戳转时间
     */
    fun convertData(millis: Long): String {
        val date = Date(millis)
        val sf = SimpleDateFormat("HH:mm", Locale.CHINA)
        return sf.format(date)
    }
}