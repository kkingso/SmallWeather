package com.kkw.smallweather.utils

import android.annotation.SuppressLint
import android.content.Context

object ResourceUtil {

    @SuppressLint("DiscouragedApi")
    fun getImageResId(context: Context?, imageName: String?): Int {
        var resId = 0
        imageName?.let {
            resId = context?.resources?.getIdentifier(it, "drawable", context.packageName) ?: 0
        }
        return resId
    }
}