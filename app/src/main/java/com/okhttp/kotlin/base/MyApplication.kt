package com.okhttp.kotlin.base

import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import com.gang.library.BaseApp
import com.gang.library.common.user.Config
import com.gang.okhttp.initOkHttp
import com.gang.tools.kotlin.ToolsConfig
import com.gang.tools.kotlin.utils.initToolsUtils
import com.okhttp.kotlin.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor

/**
 * @ProjectName: JetPack_Simple
 * @Package: com.simple.kotlin
 * @ClassName: MyApplication
 * @Description: java类作用描述
 * @Author: haoruigang
 * @CreateDate: 2022/3/7 16:30
 */
class MyApplication : BaseApp() {
    override fun onCreate() {

        Config.statusBarEnabled = false

        super.onCreate()

        ToolsConfig.isShowLog = BuildConfig.DEBUG

        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)

        initToolsUtils(this)

        val loggingInterceptor = HttpLoggingInterceptor { message ->
            Log.e(initOkHttp.TAG, "initClient: $message")
        }
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        initOkHttp.initVersionupdate()
            .addInterceptor(loggingInterceptor)
            // debug正式环境要关闭去掉（会和loggingInterceptor打印相同日志）
            .debug(TAG, ToolsConfig.isShowLog)
    }

    override fun onTerminate() {
        super.onTerminate()
        ARouter.getInstance().destroy()
    }
}