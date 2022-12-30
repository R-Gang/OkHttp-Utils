package com.okhttp.kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.launcher.ARouter
import com.gang.okhttp.bean.BaseDataModel
import com.gang.tools.kotlin.dimension.dpF
import com.gang.tools.kotlin.utils.showToast
import com.okhttp.kotlin.base.Constants
import com.okhttp.kotlin.databinding.ActivityMainBinding
import com.okhttp.kotlin.http.ApiCallBack
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
        mainBinding.apply {
            loadingFrame.noInfo?.apply {
                textSize = 16.dpF
                setTextColor(getColor(R.color.b0b2b6))
            }
        }
        mainBinding.mainData = this

    }

    fun demoOkhttp() {
        /**
         * haoruigang 2018-3-30 10:31:11   获取验证码接口
         */
        HttpManager.instance.clientConfig("HttpApiActivity",
            object : ApiCallBack<BaseDataModel<Objects>>() {
                override fun onSuccess(data: BaseDataModel<Objects>?) {
                    Logger.e("copyMode===the src is not existed")

                    if (data != null) {
                        mainBinding.loadingFrame.delayShowContainer(true)
                    } else {
                        mainBinding.loadingFrame.setNoShown(true)
                    }
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


    private var headImage: String? = ""
    fun aliyunOss() {
        /*val mImageName =
            DateUtils.getCurTimeLong("yyyyMMddHHmmss") + UserManager.INSTANCE.userData.user_id + ".jpg"
        //Url
        if (mImageName != "") {
            headImage = Config.OSS_URL + mImageName
        }*/
        // 阿里云使用方式
        // AliYunOss.getInstance(this)?.upload(mImageName, "", null)
    }

}