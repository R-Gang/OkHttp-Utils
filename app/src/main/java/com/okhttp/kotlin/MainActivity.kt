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
import com.okhttp.kotlin.utils.Util
import com.orhanobut.logger.Logger
import java.net.ServerSocket
import java.net.Socket
import java.util.Objects
import kotlin.concurrent.thread


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
        HttpManager.instance.clientConfig(
            "HttpApiActivity",
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
        ARouter.getInstance().build(Constants.ACTIVITY_URL_UPDATEAPP).navigation(this)
    }


    private var headImage: String? = ""
    fun aliyunOss() {/*val mImageName =
            DateUtils.getCurTimeLong("yyyyMMddHHmmss") + UserManager.INSTANCE.userData.user_id + ".jpg"
        //Url
        if (mImageName != "") {
            headImage = Config.OSS_URL + mImageName
        }*/
        // 阿里云使用方式
        // AliYunOss.getInstance(this)?.upload(mImageName, "", null)
    }

    var server: ServerSocket? = null
    var client: Socket? = null
    fun startTCPServer() {
        mainBinding.btnStartTCP.isEnabled = false
        mainBinding.btnStopTCP.isEnabled = true
        // 模拟单片机TCP服务端
        thread {
            val buffer = ByteArray(1024)
            server = ServerSocket(13355)
            client = server?.accept()

            /// TCP参数调优
            // 设置Socket缓冲区大小（需>网络带宽时延积）
            server?.receiveBufferSize = 256 * 1024  // 256KB [1]()

            client?.sendBufferSize = 512 * 1024     // 512KB [2]()
            // 禁用Nagle算法（适用于实时传输场景）
            client?.tcpNoDelay = true
            /// TCP参数调优

            while (true) {
                try {

                    val output = client?.getOutputStream()
                    val input = client?.getInputStream()

                    val len = input?.read(buffer)
                    val hex = Util.getInstance().bytesToString(buffer).substring(0, len!! * 3)
                    val hex2 = Util.getInstance().getHexBytes(hex.replace(" ", ""))
                    output?.write(hex2)

                    //val out = PrintWriter(output, true)
                    //out.println(hex2)

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        }

        /*// APP客户端连接
        Socket("127.0.0.1", 13355).apply {
            val hex = Util.getInstance().getHexBytes("FF 10 01 00 01 00 12 FE".replace(" ", ""))
            outputStream.write(hex)
            outputStream.flush()
        }*/
    }

    fun stopTCPServer() {
        mainBinding.btnStartTCP.isEnabled = true
        mainBinding.btnStopTCP.isEnabled = false

        client?.close()
        server?.close()
    }

}