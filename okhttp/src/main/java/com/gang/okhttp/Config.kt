package com.gang.okhttp

/**
 *
 * @ProjectName:    OkHttp-Utils
 * @Package:        com.gang.okhttp
 * @ClassName:      Config
 * @Description:    项目配置参数
 * @Author:         haoruigang
 * @CreateDate:     2020/8/10 17:26
 */
object Config {

    // 阿里云配置参数
    var accessKeyId = ""
    var accessKeySecret = ""
    var ossBucket = ""
    var ENDPOINT = "http://oss-cn-beijing.aliyuncs.com/"
    var OSS_URL = "https://$ossBucket.oss-cn-beijing.aliyuncs.com/"

}