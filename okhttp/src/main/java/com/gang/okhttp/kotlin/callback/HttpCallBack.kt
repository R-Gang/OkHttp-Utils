package com.gang.okhttp.kotlin.callback

import android.app.Activity
import android.text.TextUtils
import com.gang.library.common.utils.isNetConnected
import com.gang.library.common.utils.showToast
import com.gang.library.common.view.progress.MyProgressDialog
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
abstract class HttpCallBack<T> : AbsCallback<Any?>, IHttpCallBack<T?> {

    constructor() {}

    // 是否可取消请求，默认可取消  haoruigang  2017-11-28 11:12:09
    constructor(activity: Activity, isDismiss: Boolean) {
        dialog = if (isDismiss) MyProgressDialog(activity, true) else MyProgressDialog(activity)
        dialog?.show()
    }

    constructor(activity: Activity) {
        dialog = MyProgressDialog(activity)
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
        Logger.e("接口返回数据\n$o")
//        LogUtils.e(o.toString())
        try {
            val jsonObject = JSONObject(o.toString())
            statusCode = jsonObject.optInt("status")
            data = jsonObject.optString("data")
            errorMsg = jsonObject.optString("errorMsg")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        try {
            dismiss()
            val gsonType = getTType(this.javaClass)
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
            Logger.e("Exception $e")
            onError(e)
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
        } else {
            showToast("网络错误")
        }
        onError(e)
        super.onError(call, response, e)
    }

    companion object {
        var dialog: MyProgressDialog? = null

        fun getTType(clazz: Class<*>): Type? {
            val mySuperClassType = clazz.genericSuperclass
            val types =
                (mySuperClassType as ParameterizedType?)!!.actualTypeArguments
            return if (types.isNotEmpty()) {
                types[0]
            } else null
        }

        fun dismiss() {
            if (null != dialog && dialog!!.isShowing()) dialog?.dismiss()
        }
    }
}