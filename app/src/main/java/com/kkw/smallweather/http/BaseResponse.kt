package com.kkw.smallweather.http

data class BaseResponse<T>(
    val errorCode: Int?,
    val errorMessage: String?,
    val data: T
    )