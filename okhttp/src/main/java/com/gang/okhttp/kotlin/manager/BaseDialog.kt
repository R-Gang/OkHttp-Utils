package com.gang.okhttp.kotlin.manager

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewParent
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * @CreateDate:     2022/12/7 10:32
 * @ClassName:      BaseDialog
 * @Description:    自定义Dialog类统一继承
 * @Author:         haoruigang
 */
open class BaseDialog<VB : ViewDataBinding>(
    context: Context?,
    layoutResId: Int,
    themeResId: Int = com.gang.okhttp.R.style.DialogNoTheme
) : AlertDialog(context, themeResId) {

    protected var mBinding: VB? = BaseViewDataBinding(context, layoutResId)

    protected var dialog = Builder(context, themeResId).create()

    init {
        dialog.setView(mBinding?.root)
    }
}

fun <VB : ViewDataBinding> BaseViewDataBinding(
    context: Context?,
    layoutResId: Int,
    parent: ViewParent? = null,
    attachToParent: Boolean = false
): VB {

    return DataBindingUtil.inflate(
        LayoutInflater.from(context), layoutResId, parent as ViewGroup?,
        attachToParent
    ) as VB
}

/**
 * @CreateDate:     2022/12/7 10:32
 * @ClassName:      BaseDialog
 * @Description:    自定义Dialog方法统一继承
 * @Author:         haoruigang
 */
fun BaseAlertDialog(
    context: Context?,
    mBinding: ViewDataBinding,
    cancel: Boolean = false, // 是否可点击其他区域取消，默认不可以
    themeResId: Int = com.gang.okhttp.R.style.DialogNoTheme
): AlertDialog? {
    val dialog = AlertDialog.Builder(context, themeResId).create()
    dialog.setView(mBinding.root)
    dialog?.setCancelable(cancel)
    dialog?.show()
    return dialog
}
