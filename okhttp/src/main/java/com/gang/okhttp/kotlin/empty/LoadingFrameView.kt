package com.gang.okhttp.kotlin.empty

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.gang.okhttp.R
import com.gang.okhttp.databinding.LoadingFrameLayoutBinding

/**
 * Android 抽象布局 减少视图层级
 *
 * @author 郝瑞刚
 * @Date 2018-5-13
 * @Time 上午16:47:32
 */
class LoadingFrameView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : RelativeLayout(context, attrs, defStyleAttr) {

    var mFrameBinding: LoadingFrameLayoutBinding? =
        LoadingFrameLayoutBinding.inflate(LayoutInflater.from(context))

    var llContent = mFrameBinding?.llContent

    /**
     * 无内容
     *
     * @return
     */
    var noInfoPic: ImageView? = mFrameBinding?.noInfoPic
    var noInfo: TextView? = mFrameBinding?.noInfo

    /**
     * 加载失败图片
     *
     * @return
     */
    var ivRepeatPic: ImageView? = mFrameBinding?.ivRepeatPic

    /**
     * 加载失败文字
     *
     * @return
     */
    var tvRepeatInfo: TextView? = mFrameBinding?.tvRepeatInfo

    /**
     * 加载失败按钮
     *
     * @return
     */
    var tvTry: TextView? = mFrameBinding?.tvTry

    // 当前布局显示状态
    private var lastItem = 0


    override fun onFinishInflate() {
        super.onFinishInflate()
        // 这里,自定义的layout才解析完,这时能拿到用户自定义的xml布局体,他的位置在最后
        val index = NO_ITEM + 1 // 从此处起始,添加所有用户自定义view
        // 这里不能用i++,因为removeView之后,角标自动前移.就能拿到后面的view
        while (index < childCount) {
            val childView = llContent?.getChildAt(index)
            removeView(childView)
            mFrameBinding?.container?.addView(childView)
        }
    }

    /**
     * 设置暂无内容提示信息
     *
     * @param info 设置暂无内容提示信息
     */
    fun setNoInfo(info: String?) {
        if (null != noInfo) {
            noInfo?.text = info
        }
    }

    /**
     * 设置暂无内容提示信息
     *
     * @param resId 设置暂无内容提示信息
     */
    fun setNoInfo(@StringRes resId: Int) {
        if (null != noInfo) {
            noInfo?.setText(resId)
        }
    }

    /**
     * 设置暂无图片
     *
     * @param drawable 设置暂无图片
     */
    fun setNoIcon(drawable: Drawable?) {
        if (null != noInfoPic) {
            noInfoPic?.setImageDrawable(drawable)
        }
    }

    /**
     * 设置自定义布局
     *
     * @param view 设置自定义布局
     */
    fun setCustomView(view: View?) {
        if (null != mFrameBinding?.customLl) {
            mFrameBinding?.customLl?.removeAllViews()
            mFrameBinding?.customLl?.addView(view)
        }
    }

    /**
     * 设置暂无图片
     *
     * @param resId 设置暂无图片
     */
    fun setNoIcon(@DrawableRes resId: Int) {
        if (null != noInfoPic) {
            noInfoPic?.setImageResource(resId)
        }
    }

    /**
     * 设置失败提示信息
     *
     * @param info 设置失败提示信息
     */
    fun setRepeatInfo(info: String?) {
        if (null != tvRepeatInfo) {
            tvRepeatInfo?.text = info
        }
    }

    /**
     * 设置失败提示信息
     *
     * @param resId 设置失败提示信息
     */
    fun setRepeatInfo(@StringRes resId: Int) {
        if (null != tvRepeatInfo) {
            tvRepeatInfo?.setText(resId)
        }
    }

    /**
     * 设置失败图片
     *
     * @param drawable 设置失败图片
     */
    fun setRepeatIcon(drawable: Drawable?) {
        if (null != ivRepeatPic) {
            ivRepeatPic?.setImageDrawable(drawable)
        }
    }

    /**
     * 设置失败图片
     *
     * @param resId 设置失败图片
     */
    fun setRepeatIcon(@DrawableRes resId: Int) {
        if (null != ivRepeatPic) {
            ivRepeatPic?.setImageResource(resId)
        }
    }

    /**
     * 设置失败按钮文字
     *
     * @param info 设置失败按钮文字
     */
    fun setRepeatBt(info: String?) {
        if (null != tvTry) {
            tvTry?.text = info
        }
    }

    /**
     * 设设置失败按钮文字
     *
     * @param resId 设置失败按钮文字
     */
    fun setRepeatBt(@StringRes resId: Int) {
        if (null != tvTry) {
            tvTry?.setText(resId)
        }
    }

    /**
     * 设置失败按钮
     *
     * @param resId 设置失败按钮
     */
    fun setRepeatBtB(@DrawableRes resId: Int) {
        if (null != tvTry) {
            tvTry?.setBackgroundResource(resId)
        }
    }

    /**
     * 设置进度装载显示
     *
     * @param animate true:显示 false:隐藏
     */
    fun setProgressShown(animate: Boolean) {
        setFrame(PROGRESS_ITEM, animate, false)
    }

    /**
     * 设置暂无内容显示
     *
     * @param animate true:显示 false:隐藏
     */
    fun setNoShown(animate: Boolean) {
        setFrame(NO_ITEM, animate, false)
    }

    /**
     * 设置布局体显示
     *
     * @param animate true:显示 false:隐藏
     * @param delay   延迟加载时间(不能为负数)
     */
    fun setContainerShown(animate: Boolean, delay: Int) {
        postDelayed({
            setFrame(
                CONTAINER_ITEM,
                animate,
                false
            )
        }, delay.toLong())
    }

    /**
     * 设置自定义布局体显示
     *
     * @param animate true:显示 false:隐藏
     */
    fun setCustomShown(animate: Boolean) {
        setFrame(CUSTOM_ITEM, animate, false)
    }

    /**
     * 设置错误显示
     *
     * @param animate true:显示 false:隐藏
     */
    fun setErrorShown(
        animate: Boolean? = true,
        clickListener: OnClickListener? = null,
    ) {
        if (null != clickListener) {
            tvTry?.setOnClickListener(clickListener)
            tvTry?.visibility = View.VISIBLE
        } else tvTry?.visibility = View.GONE
        setFrame(ERROR_ITEM, animate, false)
    }

    /**
     * 延迟设置布局体
     *
     * @param animate
     */
    fun delayShowContainer(animate: Boolean) {
        setContainerShown(animate,
            DELAY_MILLIS
        )
    }

    fun isStatus(status: Int): Boolean {
        return lastItem == status
    }

    /**
     * 设置显示桢
     *
     * @param item
     */
    fun setFrame(item: Int) {
        setFrame(item, false, true)
    }

    /**
     * 设置展示桢view
     *
     * @param item      当前展示桢
     * @param animate   是否显示动画
     * @param unChecked 无须检测last==当前指定桢
     */
    private fun setFrame(
        item: Int,
        animate: Boolean? = false,
        unChecked: Boolean,
    ) {
        if (unChecked || item != lastItem) {
            val showView = llContent?.getChildAt(item)
            val closeView = llContent?.getChildAt(lastItem)
            if (animate == true) {
                val fadeAnim = ObjectAnimator.ofFloat(showView, "alpha", 0f, 1f)
                fadeAnim.duration = 200
                fadeAnim.start()
            }
            showView?.clearAnimation()
            closeView?.clearAnimation()
            showView?.visibility = View.VISIBLE
            closeView?.visibility = View.GONE
            lastItem = item
        }
    }

    /**
     * 设置背景色
     *
     * @param color
     */
    fun setBackGroundColor(color: Int) {
        setBackgroundColor(color)
    }

    companion object {
        const val DELAY_MILLIS = 300
        // 各种状态桢标志
        /**
         * 显示内容
         */
        const val CONTAINER_ITEM = 0

        /**
         * 显示加载
         */
        const val PROGRESS_ITEM = 1

        /**
         * 显示错误
         */
        const val ERROR_ITEM = 2

        /**
         * 显示自定义
         */
        const val CUSTOM_ITEM = 3

        /**
         * 显示暂无内容
         */
        const val NO_ITEM = 4
    }

    init {
        addView(mFrameBinding?.root)
        val a = context.obtainStyledAttributes(attrs, R.styleable.LoadingFrameView)
        val noinfo = a.getString(R.styleable.LoadingFrameView_fv_noInfo)
        val repeatInfo = a.getString(R.styleable.LoadingFrameView_fv_repeatInfo)
        val bt = a.getString(R.styleable.LoadingFrameView_fv_repeatBt)
        if (!TextUtils.isEmpty(noinfo)) setNoInfo(noinfo)
        if (!TextUtils.isEmpty(repeatInfo)) setRepeatInfo(repeatInfo)
        if (!TextUtils.isEmpty(bt)) setRepeatBt(bt)
        setNoIcon(a.getResourceId(R.styleable.LoadingFrameView_fv_noIcon, R.mipmap.icon_empty))
        setRepeatIcon(
            a.getResourceId(
                R.styleable.LoadingFrameView_fv_repeatIcon,
                R.mipmap.icon_wifi
            )
        )
        a.recycle()
        //setFrame(PROGRESS_ITEM)
    }
}