package com.gang.okhttp.oss.callback

/**
 * Created by haoruigang on 2017/8/31.
 */
open interface ProgressCallback<T1, T2> : Callback<T1, T2> {
    fun onProgress(request: T1, currentSize: Long, totalSize: Long)
}