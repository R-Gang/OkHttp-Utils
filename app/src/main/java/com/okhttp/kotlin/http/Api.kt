package com.okhttp.kotlin.http

import android.text.TextUtils
import com.gang.library.common.store.SpExt.getSpValue
import com.gang.okhttp.kotlin.OkHttpUtils
import com.gang.okhttp.kotlin.callback.HttpCallBack
import com.lzy.okhttputils.model.HttpHeaders
import com.okhttp.kotlin.base.Constants
import java.io.File

/**
 *
 * @ClassName:      haoruigang
 * @Description:    java类作用描述
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
     * @param tag
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

                OkHttpUtils.instance.getHeaderJsonRequest(
                    tag,
                    url,
                    params,
                    getHeaders(),
                    null,
                    callBack
                )
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
     * @param tag
     * @param url
     * @param map
     * @param json
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

                OkHttpUtils.instance.postHeaderJsonRequest(
                    tag,
                    url,
                    params,
                    getHeaders(),
                    null,
                    json,
                    callBack
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            OkHttpUtils.instance.postOkHttpJsonRequest(tag, url, map, callBack)
        }
    }

    /**
     * 上传附件
     * post
     * @param tag
     * @param url
     * @param file  // 每次只能上传一张
     * @param callBack
     */
    fun uploadFile(tag: String?, url: String, file: File, callBack: HttpCallBack<*>?) {
        com.lzy.okhttputils.OkHttpUtils
            .post(url)
            .tag(tag)
            .headers(getHeaders(true))
            .addFileParams("file", arrayListOf(file)) // 附件
            .execute(callBack)
    }

    /**
     * 请求头
     * @return header
     */
    fun getHeaders(isUploadFile: Boolean = false): HttpHeaders {
        val access_token = getSpValue("access_token", "").toString()
        val header = HttpHeaders()
        /*header.put("Accept", "application/json")*/
        if (TextUtils.isEmpty(access_token)) {
            header.put("Content-Type", "application/x-www-form-urlencoded")
            header.put("Authorization", Constants.Authorization)
        } else if (isUploadFile) {
            // 上传File请求头
            header.put("Content-Type", "multipart/form-data")
            header.put("Authorization", "Bearer $access_token")
        } else {
            header.put("Content-Type", "application/json")
            header.put("Authorization", "Bearer $access_token")
        }
        return header
    }

    companion object {

        val instance: Api
            get() = SingletonHolder.INSTANCE
    }

    private object SingletonHolder {
        var INSTANCE: Api = Api()
    }

}