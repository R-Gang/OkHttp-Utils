package com.okhttp.kotlin.http

import android.app.Activity
import android.text.TextUtils
import com.gang.okhttp.kotlin.callback.HttpCallBack
import com.gang.tools.kotlin.utils.LogUtils
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import okhttp3.Call
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject

/**
 *
 * @ClassName:      haoruigang
 * @Description:     java类作用描述
 * @Author:         haoruigang
 * @CreateDate:     2021/9/6 15:44
 * @UpdateUser:     更新者：
 * @UpdateDate:     2021/9/6 15:44
 * @UpdateRemark:   更新说明：
 * @Version:
 */
abstract class ApiCallBack<T> : HttpCallBack<T> {

    constructor() : super()

    constructor(activity: Activity) : super(activity)

    constructor(activity: Activity, isDismiss: Boolean) : super(activity, isDismiss)

//    constructor() : this()

//    constructor(activity: Activity) : this(activity)

    // 是否可取消请求，默认可取消  haoruigang  2017-11-28 11:12:09
//    constructor(activity: Activity) : super(activity) {
//        dialog = LoadingDialog(activity)
//        dialog?.setLoadingText("加载中")
//            ?.setSuccessText("加载成功") //显示加载成功时的文字
//            ?.show()
//    }


    //  成功回调
    override fun onSuccess(o: Any?, call: Call?, response: Response?) {
        response?.apply {
            Logger.e(TAG + ":\n${call?.request()}")
            try {
                val jsonObject = JSONObject(o.toString())
                statusCode = jsonObject.optInt("code")
                data = jsonObject.optString("data")
                errorMsg = jsonObject.optString("msg")
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            if (code == 200) {
                LogUtils.e(TAG, "接口返回数据 $o")
                try {
                    val gsonType = getTType(this@ApiCallBack.javaClass)
                    if (statusCode == 0 && !TextUtils.isEmpty(o.toString())) {
                        if ("class java.lang.String" == gsonType.toString()) {
                            onSuccess(o.toString() as T)
                        } else {
                            val o1: T = Gson().fromJson(o.toString(), gsonType)
                            onSuccess(o1)
                        }
                    } else {
                        onFail(statusCode, errorMsg)
                    }
                } catch (e: Exception) {
                    LogUtils.e(TAG, "Exception $e")
                    onError(e)
                }
            } else {
                LogUtils.e(TAG, "接口错误 $o")
                onFail(statusCode, errorMsg)
            }

            errorMsg?.apply {
                if (contains("token expire")) { // TODO 重新登录

                }
            }
        }
        dismiss()
    }

    companion object {
        var statusCode = 0
        var data: String? = null
        var errorMsg: String? = null

//        var dialog: LoadingDialog? = null

//        fun dismiss() {
//            dialog?.close()
//        }
    }

}