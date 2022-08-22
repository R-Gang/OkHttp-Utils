# kotlin Okhttp封装

引入方式：

    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }

    dependencies {
         implementation 'com.github.R-Gang:OkHttp-Utils:latest.integration'
    }

## Usage

## 一行代码实现Okhttp网络请求封装工具

### 调用扩展OkHttpUtils

	getRequest

```
   默认get调用
   OkHttpUtils.instance.getOkHttpJsonRequest(tag, url, map, callBack)
   get自定义请求头、请求参数处理
   OkHttpUtils.instance.getHeaderJsonRequest(tag, url, params, header, null, callBack)
```

	postRequest

```
   post默认调用
   OkHttpUtils.instance.postOkHttpJsonRequest(tag, url, map, callBack)
   post自定义请求头、请求参数处理
   OkHttpUtils.instance.getHeaderJsonRequest(tag, url, params, header, null, callBack)
   post自定义请求头、请求参数处理、json请求
   OkHttpUtils.instance.postHeaderJsonRequest(tag,url, params, header, null, json, callBack)
```

### 版本更新封装

	kotlin使用示例

```

    Config.isOpenVersionUpdate = true
    initOkHttp.initVersionupdate()
        
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
						"ifUp") == "1"
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
					serverVersionName)
				&& clientVersionCode < serverVersionCode && clientVersionName != serverVersionName
			) {
				LogUtils.d(
					"UpdateCallback22222",
					"$clientVersionCode:$serverVersionCode:$clientVersionName:$serverVersionName"
				)
				// 使用方式1，调用系统默认的样式
				// updateAppManager.showDialogFragment()
				// 使用方式2，调用自定义的样式
				showDialogFragment(this@UpdateAppActivity,
					updateApp,
					updateAppManager)
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
```

## Reference

[okhttp-OkGo](https://github.com/jeasonlzy/okhttp-OkGo)

[AppUpdate](https://github.com/WVector/AppUpdate)
