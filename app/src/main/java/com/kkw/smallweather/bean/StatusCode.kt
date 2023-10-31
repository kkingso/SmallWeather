package com.kkw.smallweather.bean

import java.io.IOException
import java.net.SocketException

/**
 * 错误状态码
 */
@Deprecated("枚举类太占内存")
enum class StatusCode(val code: Int, val message: String) {
    SUCCESS(200, "请求成功"),
    NO_NEED(204, "请求成功，但你查询的地区暂时没有你需要的数据。"),
    ERROR(400, "请求错误，可能包含错误的请求参数或缺少必选的请求参数。"),
    FAILURE(401, "认证失败，可能使用了错误的KEY、数字签名错误、KEY的类型错误。"),
    LIMIT(402, "超过访问次数或余额不足以支持继续访问服务，你可以充值、升级访问量或等待访问量重置。"),
    DENIED(403, "无访问权限，可能是绑定的PackageName、BundleID、域名IP地址不一致，或者是需要额外付费的数据。"),
    NO_DATA(404, "查询的数据或地区不存在。"),
    OPM_LIMIT(429, "超过限定的QPM（每分钟访问次数)"),
    TIMEOUT(500, "无响应或超时，接口服务异常请联系我们")
}

sealed class BaseHttpException(val errorCode: Int?, private val errorMessage: String?, exception: Throwable?): Exception(errorMessage) {

    companion object {

        fun getErrorMessage(errorCode: Int?): String {
            return when (errorCode) {
                204 -> "请求成功，但你查询的地区暂时没有你需要的数据。"
                400 -> "请求错误，可能包含错误的请求参数或缺少必选的请求参数。"
                401 -> "认证失败，可能使用了错误的KEY、数字签名错误、KEY的类型错误。"
                402 -> "超过访问次数或余额不足以支持继续访问服务，你可以充值、升级访问量或等待访问量重置。"
                403 -> "无访问权限，可能是绑定的PackageName、BundleID、域名IP地址不一致，或者是需要额外付费的数据。"
                404 -> "查询的数据或地区不存在。"
                429 -> "超过限定的QPM（每分钟访问次数)"
                500 -> "无响应或超时，接口服务异常请联系我们"
                else -> ""
            }
        }

        fun generateException(throwable: Throwable?): BaseHttpException {
            return when (throwable) {
                is BaseHttpException -> throwable
                is SocketException, is IOException -> NetworkBadException("网络请求失败", throwable)
                else -> UnknownException("未知错误", throwable)
            }
        }

        /**
         * 通过throwable异常信息构建一个BaseWeather<T>类型
         */
        fun error(throwable: Throwable?): BaseWeather<*> {
            val exception = throwable as BaseHttpException
            return BaseWeather(
                exception.errorCode,
                getErrorMessage(exception.errorCode),
                null,
                null,
                null
            )
        }
    }
}

/** * 因网络原因致使 API 请求失败 * @param errorMessage * @param realException */
class NetworkBadException(errorMessage: String, realException: Throwable) :
    BaseHttpException(10086, errorMessage, realException)

/** * API 请求成功了，但 code != successCode * @param bean */
class ServerCodeNoSuccessException(bean: BaseWeather<*>) :
    BaseHttpException(bean.errorCode, getErrorMessage(bean.errorCode), null)

/** * 未知错误 * @param errorMessage * @param realException */
class UnknownException(errorMessage: String, realException: Throwable?) :
    BaseHttpException(10087, errorMessage, realException)