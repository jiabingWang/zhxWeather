package com.zhx.weather.net

import android.util.ArrayMap
import com.google.gson.Gson
import com.zhx.weather.util.logE
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * 描述：当前项目，最底层的网络请求
 */
val gson = Gson()
lateinit var okHttpClient: OkHttpClient
fun initOkHttp() {
    val builder = OkHttpClient().newBuilder()
    builder.connectTimeout(30000, TimeUnit.MILLISECONDS)
    builder.readTimeout(30000, TimeUnit.MILLISECONDS)
    builder.writeTimeout(30000, TimeUnit.MILLISECONDS)
    builder.retryOnConnectionFailure(true)
    okHttpClient = builder.build()
}

/**
 *
 */
fun <T> Request.mallHttp(
    cls: Class<T>,
    success: (T) -> Unit,
    fail: (Throwable) -> Unit
): Call {
    return okHttpClient.newCall(this).apply {
        enqueue(object : Callback {
            override fun onFailure(p0: Call, p1: IOException) {
                logE("httpGetError----->请求：${this@mallHttp}\n----->错误：${p1.message} ")
                fail.invoke(p1)
            }

            override fun onResponse(p0: Call, p1: Response) {
                try {
                    val bodyStr = p1.body()?.string()
                    if (p1.code() in 200..299) {
                        success.invoke(gson.fromJson(bodyStr, cls))
                    } else {
                        fail.invoke(Exception("系统异常"))
                    }
                } catch (e: Throwable) {
                    e.printStackTrace()
                    logE("httpGetError----->请求：${this@mallHttp}----->错误：${e.message}")
                    fail.invoke(e)
                }
            }
        })
    }
}

/**
 * 构建请求体
 */
fun <T> String.httpPost(
    formData: ArrayMap<String, String>,
    cls: Class<T>,
    success: (T) -> Unit,
    fail: (Throwable) -> Unit
): Call {
    return Request.Builder()
        .url(this)
        .post(formData.createFormBody())
        .build()
        .mallHttp(cls, success, fail)
}

fun ArrayMap<String, String>.createFormBody(): RequestBody {
    val formBody = FormBody.Builder()
    for ((key, value) in this) {
        formBody.add(key, value)
    }
    return formBody.build()
}

