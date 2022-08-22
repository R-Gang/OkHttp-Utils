package com.gang.okhttp.kotlin

import com.gang.tools.kotlin.utils.transMap2String
import com.lzy.okhttputils.OkHttpUtils
import com.lzy.okhttputils.callback.AbsCallback
import com.lzy.okhttputils.model.HttpHeaders
import com.lzy.okhttputils.model.HttpParams
import com.orhanobut.logger.Logger

/**
 *
 * @ProjectName:    gang
 * @Package:        com.gang.kotlin.okhttp
 * @ClassName:      OkHttpUtils
 * @Description:     java类作用描述
 * @Author:         haoruigang
 * @CreateDate:     2020/8/3 16:05
 */
class OkHttpUtils {


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
        callBack: AbsCallback<*>?,
    ) {
        getHeaderJsonRequest(tag, url, map as HashMap<String, String>?, null, null, callBack)
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
        callBack: AbsCallback<*>?,
    ) {
        postHeaderJsonRequest(tag, url, map as HashMap<String, String>?, null, null, "", callBack)
    }

    /**
     * 参数调用
     * get
     * @param url
     * @param map
     * @param callBack
     */
    fun getHeaderJsonRequest(
        tag: String?,
        url: String,
        map: HashMap<String, String>?,
        headers: HttpHeaders?,
        httpParams: HttpParams?,
        callBack: AbsCallback<*>?,
    ) {
        if (map != null) {
            Logger.e(tag + "\nget请求:$url,参数---${transMap2String(map)}")

            val params = HashMap<String, String>()
            params.putAll(map) // 不加密的参数

            OkHttpHandlerData(headers, httpParams, object : HandlerParams {
                override fun handlerData(header: HttpHeaders, httpParam: HttpParams) {
                    try {
                        OkHttpUtils.get(url)
                            .headers(header)
                            .params(params)
                            .params(httpParam)
                            .execute(callBack)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            })
        } else {
            OkHttpUtils.get(url)
                .execute(callBack)
        }
    }

    /**
     * 参数调用
     * post
     * @param url
     * @param map
     * @param callBack
     */
    fun postHeaderJsonRequest(
        tag: String?,
        url: String,
        map: HashMap<String, String>?,
        headers: HttpHeaders?,
        httpParams: HttpParams?,
        json: String? = "",
        callBack: AbsCallback<*>?,
    ) {
        if (map != null) {
            try {
                Logger.e(tag + "\npost请求:$url,参数---${transMap2String(map)}")

                val params = HashMap<String, String>()
                params.putAll(map) // 不加密的参数

                OkHttpHandlerData(headers, httpParams, object : HandlerParams {
                    override fun handlerData(header: HttpHeaders, httpParam: HttpParams) {
                        try {
                            OkHttpUtils.post(url)
                                .headers(header)
                                .params(params)
                                .params(httpParam)
                                .upJson(json)
                                .execute(callBack)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                })
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            OkHttpUtils.post(url)
                .execute(callBack)
        }
    }

    /**
     * 参数处理
     */
    fun OkHttpHandlerData(
        headers: HttpHeaders?,
        httpParams: HttpParams?,
        callBack: HandlerParams?,
    ) {
        // val access_token = getSpValue("access_token", "")

        var header = HttpHeaders()
        if (headers == null) {
            header.put("Content-Type", "application/x-www-form-urlencoded")
            // header.put("Authorization", "Bearer $access_token")
            header.put("Accept", "application/json")
        } else {
            header = headers
        }

        var httpParam = HttpParams()
        if (httpParams != null) {
            httpParam = httpParams
        }

        callBack?.handlerData(header, httpParam)
    }

    interface HandlerParams {
        fun handlerData(header: HttpHeaders, httpParam: HttpParams)
    }

    companion object {

        val instance: com.gang.okhttp.kotlin.OkHttpUtils
            get() = SingletonHolder.INSTANCE
    }

    private object SingletonHolder {
        var INSTANCE: com.gang.okhttp.kotlin.OkHttpUtils = OkHttpUtils()
    }

}
