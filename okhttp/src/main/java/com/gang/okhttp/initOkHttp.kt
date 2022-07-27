package com.gang.okhttp

import android.util.Log
import com.gang.library.common.user.Config
import com.lzy.okhttputils.OkHttpUtils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * @CreateDate:     2022/7/22 15:17
 * @ClassName:      initOkHttp
 * @Description:    类作用描述
 * @Author:         haoruigang
 */
object initOkHttp {

    val TAG = "initOkhttp"

    fun initVersionupdate() {
        // 版本更新
        if (Config.isOpenVersionUpdate) {
            // okhttp-utils
            okHttpUtils(20 * 1000).debug(TAG, true)
        }
    }

    fun okHttpUtils(timeout: Long): OkHttpUtils {
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            Log.e(TAG, "initClient: $message")
        }
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .connectTimeout(timeout, TimeUnit.MILLISECONDS)
            .readTimeout(timeout, TimeUnit.MILLISECONDS)
            .writeTimeout(timeout, TimeUnit.MILLISECONDS)
            .addInterceptor(loggingInterceptor)
        return OkHttpUtils.getInstance()
    }

}