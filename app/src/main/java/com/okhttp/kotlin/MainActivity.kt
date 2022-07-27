package com.okhttp.kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.launcher.ARouter
import com.gang.app.common.http.ApiCallBack
import com.gang.library.bean.BaseDataModel
import com.gang.library.common.utils.showToast
import com.okhttp.kotlin.base.Constants
import com.okhttp.kotlin.databinding.ActivityMainBinding
import com.okhttp.kotlin.http.HttpManager
import com.orhanobut.logger.Logger
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 原始方式
        // setContentView(R.layout.activity_main)

        /*
        方式1 视图绑定
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)
        */

        /*
        方式2 数据绑定
        */
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        mainBinding.mainData = this

    }

    fun demoOkhttp() {
        /**
         * haoruigang 2018-3-30 10:31:11   获取验证码接口
         */
        HttpManager.instance.clientConfig("HttpApiActivity",
            object : ApiCallBack<BaseDataModel<Objects>>() {
                override fun onSuccess(date: BaseDataModel<Objects>?) {
                    Logger.e("copyMode===the src is not existed")
                }

                override fun onFail(statusCode: Int?, errorMsg: String?) {
                    showToast(errorMsg)
                }

                override fun onError(throwable: Throwable?) {
                    showToast(throwable.toString(), 3000)
                }
            })
    }

    fun onToUpdateApp() {
        ARouter.getInstance()
            .build(Constants.ACTIVITY_URL_UPDATEAPP)
            .navigation(this)
    }

}