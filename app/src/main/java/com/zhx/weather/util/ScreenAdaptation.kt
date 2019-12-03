package com.zhx.weather.util

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.res.Configuration

/**
 * 描述：今日头条,屏幕适配
 */
object ScreenAdaptation {
    /**
     * 屏幕适配，设计稿宽度，单位dp
     */
    private const val SCREEN_ADAPTATION_UI_DP_WIDTH = 360
    private var customDensity: Float? = null
    private var customFontScaleDensity: Float? = null

    fun setCustomDensity(activity: Activity, application: Application) {
        val dm = application.resources.displayMetrics
        if (customDensity == null) {
            customDensity = dm.density
            customFontScaleDensity = dm.scaledDensity
            application.registerComponentCallbacks(object : ComponentCallbacks {
                override fun onLowMemory() {

                }

                override fun onConfigurationChanged(newConfig: Configuration?) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        customFontScaleDensity = application.resources.displayMetrics.scaledDensity
                    }
                }
            })
        }
        val targetDensity = dm.widthPixels.toFloat() / SCREEN_ADAPTATION_UI_DP_WIDTH
        val targetFontScaleDensity = targetDensity * (customFontScaleDensity!! / customDensity!!)
        val targetDpi = (160 * targetDensity).toInt()
        val activityDm = activity.resources.displayMetrics
        activityDm.density = targetDensity
        activityDm.scaledDensity = targetFontScaleDensity
        activityDm.densityDpi = targetDpi
    }
}