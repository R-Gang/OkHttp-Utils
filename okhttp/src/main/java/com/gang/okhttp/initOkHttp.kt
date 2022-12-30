package com.gang.okhttp

import com.lzy.okhttputils.OkHttpUtils
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * @CreateDate:     2022/7/22 15:17
 * @ClassName:      initOkHttp
 * @Description:    类作用描述
 * @Author:         haoruigang
 */
object initOkHttp {

    val TAG = "initOkhttp"


    fun initVersionupdate(): OkHttpUtils {
        // 版本更新
        return okHttpUtils(20 * 1000)
    }

    fun okHttpUtils(timeout: Long): OkHttpUtils {
        // okhttp-utils
        OkHttpClient.Builder()
            .connectTimeout(timeout, TimeUnit.MILLISECONDS)
            .readTimeout(timeout, TimeUnit.MILLISECONDS)
            .writeTimeout(timeout, TimeUnit.MILLISECONDS)
        return OkHttpUtils.getInstance()
    }

}