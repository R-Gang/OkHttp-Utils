package com.okhttp.kotlin.base

import com.alibaba.android.arouter.launcher.ARouter
import com.gang.kotlin.OkHttpInit
import com.gang.library.BaseApplication
import com.gang.library.common.user.Config
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
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

        Config.statusBarEnabled = false

        super.onCreate()

        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }

        ARouter.init(this)

        OkHttpInit.initVersionupdate()

    }

    override fun onTerminate() {
        super.onTerminate()
        ARouter.getInstance().destroy()
    }
}