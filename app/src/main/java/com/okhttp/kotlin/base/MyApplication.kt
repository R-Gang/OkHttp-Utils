package com.okhttp.kotlin.base

import com.alibaba.android.arouter.launcher.ARouter
import com.gang.library.BaseApplication
import com.gang.okhttp.Config
import com.gang.okhttp.initOkHttp
import com.gang.tools.kotlin.utils.initToolsUtils
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

        com.gang.library.common.user.Config.statusBarEnabled = false

        super.onCreate()

        com.gang.tools.kotlin.Config.isShowLog = BuildConfig.DEBUG

        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)

        initToolsUtils(this)

        Config.isOpenVersionUpdate = true
        initOkHttp.initVersionupdate()


    }

    override fun onTerminate() {
        super.onTerminate()
        ARouter.getInstance().destroy()
    }
}