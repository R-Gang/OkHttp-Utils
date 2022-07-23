package com.okhttp.kotlin.http

import com.gang.kotlin.okhttp.OkHttpUtils
import com.gang.kotlin.okhttp.callback.HttpCallBack
import com.gang.library.common.utils.getSpValue
import com.lzy.okhttputils.model.HttpHeaders

/**
 *
 * @ClassName:      haoruigang
 * @Description:     java类作用描述
 * @Author:         haoruigang
 * @CreateDate:     2021/9/6 14:45
 * @UpdateUser:     更新者：
 * @UpdateDate:     2021/9/6 14:45
 * @UpdateRemark:   更新说明：
 * @Version:
 */
class Api {


    /**
     * 参数调用
     * get
     * @param url
     * @param map
     * @param callBack
     */
    fun getOkHttpJsonRequest(
        tag: String?,
        url: String,
        map: Map<String, String>?,
        callBack: HttpCallBack<*>?,
    ) {
        if (map != null) {
            try {
                val params = HashMap<String, String>()
                params.putAll(map as HashMap) // 不加密的参数

                val access_token = getSpValue("access_token", "")
                val header = HttpHeaders()
                header.put("Content-Type", "application/x-www-form-urlencoded")
                header.put("Authorization", "Bearer $access_token")
                header.put("Accept", "application/json")
                OkHttpUtils.instance.getHeaderJsonRequest(tag, url, params, header, null, callBack)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            OkHttpUtils.instance.getOkHttpJsonRequest(tag, url, map, callBack)
        }
    }

    /**
     * 参数调用
     * post
     * @param url
     * @param map
     * @param callBack
     */
    fun postOkHttpJsonRequest(
        tag: String?,
        url: String,
        map: Map<String, String>?,
        json: String?,
        callBack: HttpCallBack<*>?,
    ) {
        if (map != null) {
            try {
                val params = HashMap<String, String>()
                params.putAll(map as HashMap) // 不加密的参数

                val access_token = getSpValue("access_token", "")
                val header = HttpHeaders()
                header.put("Content-Type", "application/x-www-form-urlencoded")
                header.put("Authorization", "Bearer $access_token")
                header.put("Accept", "application/json")
                OkHttpUtils.instance.postHeaderJsonRequest(tag,
                    url,
                    params,
                    header,
                    null,
                    json,
                    callBack)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            OkHttpUtils.instance.postOkHttpJsonRequest(tag, url, map, callBack)
        }
    }


    companion object {

        val instance: Api
            get() = SingletonHolder.INSTANCE
    }

    private object SingletonHolder {
        var INSTANCE: Api = Api()
    }

}