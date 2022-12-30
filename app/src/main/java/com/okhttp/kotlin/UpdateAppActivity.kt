package com.okhttp.kotlin

import android.Manifest
import android.os.Bundle
import android.text.TextUtils
import com.alibaba.android.arouter.facade.annotation.Route
import com.gang.library.base.BaseActivity
import com.gang.library.common.Permission.requestPermission
import com.gang.okhttp.kotlin.AppHttpUtil
import com.gang.okhttp.kotlin.progress.MyProgressDialog
import com.gang.okhttp.version.UpdateBean
import com.gang.okhttp.version.UpdateCallback
import com.gang.okhttp.version.UpdateHandle
import com.gang.okhttp.version.UpdateHandle.Companion.showDialogFragment
import com.gang.tools.kotlin.utils.LogUtils
import com.github.dfqin.grantor.PermissionListener
import com.okhttp.kotlin.base.Constants
import com.okhttp.kotlin.base.Constants.ACTIVITY_URL_UPDATEAPP
import com.vector.update_app.UpdateAppBean
import com.vector.update_app.UpdateAppManager
import com.vector.update_app.utils.AppUpdateUtils
import org.json.JSONObject

/**
 * 版本更新使用方式示例
 */
@Route(path = ACTIVITY_URL_UPDATEAPP)
class UpdateAppActivity : BaseActivity() {
    override val layoutId: Int
        get() = R.layout.activity_update_app

    override fun initView(savedInstanceState: Bundle?) {

        //动态权限申请
        // Constants.REQUEST_CODE_WRITE,
        // getString(R.string.rationale_file),
        requestPermission(mContext = this,
            permissions = arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), permissAction = object : PermissionListener {
                override fun permissionGranted(permission: Array<out String>) {

                    val updateHandle = UpdateHandle(object : UpdateCallback {

                        override fun parseJson(
                            json: String,
                            updateAppBean: UpdateBean,
                        ): UpdateAppBean {

                            val updateAppBean = UpdateBean()
                            try {
                                val jsonData = JSONObject(json)
                                val jsonObject = jsonData.getJSONObject("data")
                                updateAppBean.newVersionCode =
                                    jsonObject.optString("appVersionCode")
                                updateAppBean.setUpdate(if (jsonObject.optString("ifShow") == "1") "Yes" else "No")
                                    .setNewVersion(jsonObject.optString("appVersion"))
                                    .setApkFileUrl(jsonObject.optString("apkFileUrl"))
                                    .setUpdateLog(jsonObject.optString("description")).isConstraint =
                                    jsonObject.optBoolean("constraint") && jsonObject.optString(
                                        "ifUp"
                                    ) == "1"
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                            return updateAppBean

                        }

                        override fun hasNewApp(
                            updateApp: UpdateAppBean,
                            updateAppManager: UpdateAppManager,
                        ) {

                            val clientVersionCode =
                                AppUpdateUtils.getVersionCode(this@UpdateAppActivity)
                            val serverVersionCode =
                                (updateApp as UpdateBean).newVersionCode!!.toInt()
                            val clientVersionName =
                                AppUpdateUtils.getVersionName(this@UpdateAppActivity)
                            val serverVersionName = updateApp.getNewVersion()
                            LogUtils.d(
                                "UpdateCallback11111",
                                "$clientVersionCode:$serverVersionCode:$clientVersionName:$serverVersionName"
                            )
                            //有新版本
                            if (!TextUtils.isEmpty(clientVersionName) && !TextUtils.isEmpty(
                                    serverVersionName
                                )
                                && clientVersionCode < serverVersionCode && clientVersionName != serverVersionName
                            ) {
                                LogUtils.d(
                                    "UpdateCallback22222",
                                    "$clientVersionCode:$serverVersionCode:$clientVersionName:$serverVersionName"
                                )
                                // 使用方式1，调用系统默认的样式
//                                updateAppManager.showDialogFragment()
                                // 使用方式2，调用自定义的样式
                                showDialogFragment(
                                    this@UpdateAppActivity,
                                    updateApp,
                                    updateAppManager
                                )
                            }

                        }

                    })

                    //版本更新、需导入版本更新所需依赖
                    UpdateAppManager.Builder() //当前Activity
                        .setActivity(this@UpdateAppActivity) //更新地址
                        .setUpdateUrl(Constants.VERSION_PATH)
                        .handleException { obj: java.lang.Exception -> obj.printStackTrace() } //实现httpManager接口的对象
                        .setHttpManager(AppHttpUtil())
                        .build()
                        .checkNewApp(updateHandle)
                }

                override fun permissionDenied(permission: Array<out String>) {
                    LogUtils.e("UpdateAppActivity", "TODO: WRITE_EXTERNAL_STORAGE Denied")
                }

            })
    }

    override fun initData() {
        MyProgressDialog(this).show()
    }
}