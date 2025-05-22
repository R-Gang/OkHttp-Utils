package com.okhttp.kotlin.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.gang.library.BaseApp
import com.gang.okhttp.kotlin.manager.BaseDialog
import com.okhttp.kotlin.R
import com.okhttp.kotlin.databinding.DialogLoadingBinding

/**
 * 获取 mipmap 资源图片
 */
fun loadMipmapImage(rawResName: String = "ic_nearby_guide_bg"): Bitmap {
    val res = BaseApp.appContext.resources.getIdentifier(
        rawResName,
        "mipmap",
        BaseApp.appContext.packageName
    )
    val opts = BitmapFactory.Options()
    opts.inPreferredConfig = Bitmap.Config.ARGB_8888
    opts.inScaled = false
    return BitmapFactory.decodeResource(BaseApp.appContext.resources, res, opts)
}

/**
 * 加载弹框
 * @param mContext 上下文
 * @param loadTxt 提示内容
 * @param isDismiss 是否可点击取消(默认可点击取消)
 */
class LoadingDialog(
    val mContext: Context,
    loadTxt: String = "加载中...",
    isDismiss: Boolean = true   // 可点击取消
) : BaseDialog<DialogLoadingBinding>(mContext, R.layout.dialog_loading, isDismiss) {
    private var mAnimation: Animation? = null

    init {
        startAnim()

        mBinding?.tvLoadingText?.text = loadTxt
    }

    private fun startAnim() {
        if (mAnimation == null) mAnimation =
            AnimationUtils.loadAnimation(mContext, R.anim.load_anim)
        mBinding?.ivLoadingCircle?.startAnimation(mAnimation)
    }

    override fun cancel() {
        mAnimation?.cancel()
        dialog.cancel()
        super.cancel()
    }

}