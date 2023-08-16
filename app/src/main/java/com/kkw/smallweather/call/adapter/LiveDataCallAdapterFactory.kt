package com.kkw.smallweather.call.adapter

import androidx.lifecycle.LiveData
import com.kkw.smallweather.bean.BaseWeather
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @author kaiwang
 * @date 2023/8/11
 */
class LiveDataCallAdapterFactory: CallAdapter.Factory() {

    companion object {
        fun create() = LiveDataCallAdapterFactory()
    }

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != LiveData::class.java) {
            // 并不是目标类型的话直接返回null
            return null
        }
        // 拿到LiveData包含的内部泛型类型
        val responseType = getParameterUpperBound(0, returnType as ParameterizedType)
        require(getRawType(responseType) == BaseWeather::class.java) {
            "LiveData 包含的泛型类型必须是 BaseWeather"
        }
        return LiveDataCallAdapter<Any>(responseType)
    }
}