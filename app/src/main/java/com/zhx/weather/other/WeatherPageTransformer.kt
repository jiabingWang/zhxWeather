package com.zhx.weather.other

import android.view.View
import androidx.viewpager.widget.ViewPager

/**
 * 描述：跳转透明度使天气Fg承载的ViewPager切换起来效果更佳自然
 */
class WeatherPageTransformer  :ViewPager.PageTransformer{
    override fun transformPage(page: View, position: Float) {

        when {
            position<-1 -> page.alpha = 0F
            position <=0 -> page.alpha = (1+position)
            position<=1 -> page.alpha = (1 -position)
            else -> page.alpha=0F
        }
        page.translationX = position
    }
}