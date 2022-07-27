package com.okhttp.kotlin.base

import com.alibaba.android.arouter.launcher.ARouter
import com.gang.library.BaseApplication
import com.gang.library.common.user.Config
import com.gang.okhttp.initOkHttp
import com.okhttp.kotlin.BuildConfig

/**
 * @ProjectName: JetPack_Simple
 * @Package: com.simple.kotlin
 * @ClassName: MyApplication
 * @Description: java类作用描述
 * @Author: haoruigang
 * @CreateDate: 2022/3/7 16:30
 */
class MyApplication : BaseApplication() {
    override fun onCreate() {

        Config.isShowLog = BuildConfig.DEBUG
        Config.statusBarEnabled = false

        super.onCreate()

        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }

        ARouter.init(this)

        initOkHttp.isOpenVersionUpdate = true
        initOkHttp.initVersionupdate()

    }

    override fun onTerminate() {
        super.onTerminate()
        ARouter.getInstance().destroy()
    }
}