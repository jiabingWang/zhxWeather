package com.zhx.weather.util

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.core.graphics.ColorUtils

/**
 * 描述：和UI相关的一些工具方法
 */

/**设置状态栏颜色，默认为白色*/
fun setStatusBarColor(activity: Activity,@ColorInt color: Int = Color.WHITE) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

        // 设置状态栏底色颜色
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        activity.window.statusBarColor = color

        // 判断颜色是不是亮色
        // 如果亮色，设置状态栏文字为黑色
        if (ColorUtils.calculateLuminance(color) >= 0.5) {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        }
    }
}