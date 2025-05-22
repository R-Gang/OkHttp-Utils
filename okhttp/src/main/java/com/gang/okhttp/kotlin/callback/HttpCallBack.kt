package com.gang.okhttp.kotlin.callback

import android.app.Activity
import android.text.TextUtils
import com.gang.okhttp.kotlin.progress.MyProgressDialog
import com.gang.tools.kotlin.utils.LogUtils
import com.gang.tools.kotlin.utils.isNetConnected
import com.gang.tools.kotlin.utils.showToast
import com.google.gson.Gson
import com.lzy.okhttputils.callback.AbsCallback
import com.orhanobut.logger.Logger
import okhttp3.Call
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 *
 * @ProjectName:    gang
 * @Package:        com.gang.kotlin.okhttp.callback
 * @ClassName:      HttpCallBack
 * @Description:    网络回调类
 * @Author:         haoruigang
 * @CreateDate:     2020/8/3 15:54
 */
abstract class HttpCallBack<T> : AbsCallback<Any?>, IHttpManager<T?> {

    constructor() {}

    constructor(activity: Activity) {
        dialog = MyProgressDialog(activity)
        dialog?.show()
    }

    // 是否可取消请求，默认可取消  haoruigang  2017-11-28 11:12:09
    constructor(activity: Activity, isDismiss: Boolean) {
        dialog = if (isDismiss) MyProgressDialog(activity, true) else MyProgressDialog(activity)
        dialog?.show()
    }

    //----------引入之前的代码-------------
    private var statusCode = 0
    private var data: String? = null
    private var errorMsg: String? = null

    @Throws(Exception::class)
    override fun parseNetworkResponse(response: Response?): Any? {
        return response?.body()?.string()
    }

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
            if (code() == 200) {
                LogUtils.e(TAG, "接口返回数据 $o")
                try {
                    val gsonType = getTType(this@HttpCallBack.javaClass)
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
            dismiss()
        }
    }

    // 失败后的回调
    @Throws(Exception::class)
    override fun onError(
        call: Call?,
        response: Response?,
        e: Exception?,
    ) {
        Logger.e("error ---response:$response $e")
        dismiss()
        if (!isNetConnected()) {
            showToast("NetWork Error")
        } else if (response?.code() == 503) {
            showToast("服务器重启中...")
        }
        onError(e)
    }

    companion object {

        val TAG = "HttpCallBack"

        fun getTType(clazz: Class<*>): Type? {
            val mySuperClassType = clazz.genericSuperclass
            val types = (mySuperClassType as ParameterizedType?)?.actualTypeArguments
            types?.apply {
                return if (isNotEmpty()) {
                    this[0]
                } else null
            }
            return null
        }

    }

    var dialog: MyProgressDialog? = null
    fun dismiss() {
        if (null != dialog && dialog?.isShowing() == true) dialog?.close()
    }

}