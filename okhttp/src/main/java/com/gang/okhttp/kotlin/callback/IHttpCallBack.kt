package com.gang.okhttp.kotlin.callback

/**
 *
 * @ProjectName:    gang
 * @Package:        com.gang.kotlin.okhttp.callback
 * @ClassName:      IHttpCallBack
 * @Description:    网络回调接口类
 * @Author:         haoruigang
 * @CreateDate:     2020/8/3 15:56
 */
open interface IHttpCallBack<T> {
    fun onSuccess(data: T?)
    fun onFail(statusCode: Int?, errorMsg: String?)
    fun onError(throwable: Throwable?)
}

open interface IHttpManager<T> : IHttpCallBack<T> {

    override fun onFail(statusCode: Int?, errorMsg: String?) {

    }

    override fun onError(throwable: Throwable?) {

    }

}