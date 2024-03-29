import org.gradle.api.artifacts.dsl.RepositoryHandler
import java.net.URI

/**
 * 作者: hrg
 * 创建时间: 2022-04-07 11:11:48
 * 描述: 新框架依赖项，统一管理，同时也方便其他组件引入
 */

/**
 * App
 */
object App {
    const val applicationId = "com.gang.okhttp"
}

/**
 * Android
 */
object Android {
    const val kotlin = "1.6.10"
    const val gradle = "7.0.3"
    const val compileSdkVersion = 32
    const val minSdkVersion = 26
    const val targetSdkVersion = 32
    const val versionCode = 1
    const val versionName = "1.0"

}

/**
 * 系统库依赖
 * */
object Support {

    const val junit = "junit:junit:4.13.2"
    const val junit_ext = "androidx.test.ext:junit:1.1.2"
    const val espresso_core = "androidx.test.espresso:espresso-core:3.3.0"

    const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.0.4"
    const val appcompat = "androidx.appcompat:appcompat:1.3.0"
    const val kotlin_stdlib_jdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Android.kotlin}"

    const val build_gradle = "com.android.tools.build:gradle:${Android.gradle}"
    const val kotlin_gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Android.kotlin}"
}

/**
 * 第三方库依赖
 * */
object Dependencies {
    const val android_common = "com.github.R-Gang:Android-Common:v0.1.7-beta.0" // 常用基础扩展类

    const val tools_utils = "com.github.R-Gang:Tools-Utils:v1.0.1-beta.2" // 实用工具类

    const val androidx_multidex = "androidx.multidex:multidex:2.0.1" // Dex处理

    const val arouter_register = "com.alibaba:arouter-register:1.0.2"
    const val arouter_api = "com.alibaba:arouter-api:1.5.2"
    const val arouter_compiler = "com.alibaba:arouter-compiler:1.5.2"

    const val okhttputils = "com.lzy.net:okhttputils:1.8.1" // 可以单独使用，不需要依赖下方的扩展包
    const val okhttp_logging_interceptor =
        "com.squareup.okhttp3:logging-interceptor:4.9.1" //非必要依赖，打印日志
    const val gson = "com.google.code.gson:gson:2.8.6" // gson
    const val update_app = "com.qianwen:update-app:3.5.2" // 版本更新okhttp-utils

    const val aliyun_oss = "com.aliyun.dpa:oss-android-sdk:2.9.5" // aliyun

    const val logger = "com.orhanobut:logger:2.2.0" // 日志工具类 logger
    const val loadingDialog = "com.github.ForgetAll:LoadingDialog:v1.1.2" // LoadingDialog

    const val permissions = "com.github.dfqin:grantor:2.5" // 动态权限申请

    val addRepos: (handler: RepositoryHandler) -> Unit = {
        it.google()
        it.jcenter()
        it.mavenCentral()
        it.maven { url = URI("https://maven.aliyun.com/repository/gradle-plugin") }
        it.maven { url = URI("https://maven.aliyun.com/repository/public") }
        it.maven { url = URI("https://maven.aliyun.com/repository/central") }
        it.maven { url = URI("https://maven.aliyun.com/repository/google") }
        it.maven { url = URI("https://maven.aliyun.com/repository/jcenter") }
        it.maven {
            url = URI("https://jitpack.io")
            val authToken = "jp_7hqsbgvlrlh8sua6dainpc08j4"
            credentials { username = authToken }
        }
        it.maven { url = URI("https://developer.huawei.com/repo/") }
        it.maven { url = URI("https://dl.google.com/dl/android/maven2/") }
        it.maven { url = URI("https://maven.youzanyun.com/repository/maven-releases") }
        it.maven { url = URI("https://maven.google.com") }
        it.maven { url = URI("https://dl.bintray.com/thelasterstar/maven/") }
        it.maven { url = URI("https://dl.bintray.com/kotlin/kotlin-eap") }
        it.maven { url = URI("https://dl.bintray.com/umsdk/release") }
    }
}
