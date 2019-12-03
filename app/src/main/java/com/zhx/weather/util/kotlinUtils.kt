package com.zhx.weather.util

import android.util.Log
import com.zhx.weather.common.IS_DEBUG

/**
 * 描述：一些常用的 kotlin拓展方法
 */
fun Any.logD(msg: String?) {
    if (IS_DEBUG) {
        Log.d("jiaBingD", "msg")
    }
}
fun Any.logE(msg: String?) {
    if (IS_DEBUG) {
        Log.e("jiaBingE", "msg")
    }
}
fun Any.logI(msg: String?) {
    if (IS_DEBUG) {
        Log.i("jiaBingI", "msg")
    }
}