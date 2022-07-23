package com.gang.okhttp

import com.gang.library.common.user.Config
import com.lzy.okhttputils.OkHttpUtils
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * @CreateDate:     2022/7/22 15:17
 * @ClassName:      OkHttpInit
 * @Description:    类作用描述
 * @Author:         haoruigang
 */
object OkHttpInit {

    fun initVersionupdate() {
        // 版本更新
        if (Config.isOpenVersionUpdate) {
            // okhttp-utils
            timeout(20 * 1000).debug("okHttp", true)
        }
    }

    fun timeout(timeout: Long): OkHttpUtils {
        OkHttpClient.Builder()
            .connectTimeout(timeout, TimeUnit.MILLISECONDS)
            .readTimeout(timeout, TimeUnit.MILLISECONDS)
            .writeTimeout(timeout, TimeUnit.MILLISECONDS)
        return OkHttpUtils.getInstance()
    }
}