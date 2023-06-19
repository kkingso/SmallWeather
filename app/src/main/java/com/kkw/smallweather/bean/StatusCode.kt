package com.kkw.smallweather.bean

/**
 * 错误状态码
 */
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