package com.okhttp.kotlin.base

/**
 *
 * @ProjectName:    JetPack_Simple
 * @Package:        com.simple.kotlin.base
 * @ClassName:      Constants
 * @Description:    路由路径
 * @Author:         haoruigang
 * @CreateDate:     2022/3/7 16:42
 */
object Constants {

    val Authorization = "Basic aGVyby1kYW5hLXVzZXI6aGVyby1kYW5hLXVzZXI="

    const val ACTIVITY_URL_UPDATEAPP = "/simple/UpdateAppActivity"


    const val HOST_RELEASE = "https://appapis.dpm.org.cn"
    val VERSION_PATH: String = HOST_RELEASE + "/gugong/mrgg/getAndroidVersion"

    val REQUEST_CODE_WRITE: Int = 9090

    // 是否测试服
    var isTest = true

    //
    private val BASE_API =
        if (isTest) "http://pre-api.daliangshijie.cc" else ""

    // 发送手机验证码
    var SENDVCODE = "$BASE_API/api/system/sendVcode.do"

    // 客户端基本配置
    val CLIENT_CONFIG = "$BASE_API/api/system/client_config/get.do"


}