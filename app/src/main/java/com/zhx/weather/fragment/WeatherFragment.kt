package com.zhx.weather.fragment

import android.os.Bundle
import android.view.View
import com.zhx.weather.R
import com.zhx.weather.base.BaseFragment
import com.zhx.weather.dynamic.ShortWeatherInfo
import com.zhx.weather.dynamic.SunnyType
import com.zhx.weather.util.textFrom
import kotlinx.android.synthetic.main.fragment_weather.*

/**
 * 描述：
 */
class WeatherFragment(private val city: String) : BaseFragment() {
    companion object {
        fun newInstance(city: String): WeatherFragment {
            return WeatherFragment(city)
        }
    }

    override fun getLayoutResOrView() = R.layout.fragment_weather

    override fun initData() {

    }

    override fun initUi(savedInstanceState: Bundle?) {
        showDynamicWeather()
    }

    override fun getClickView(): List<View?>? {
        return listOf()
    }

    override fun onSingleClick(view: View) {

    }

    override fun onResume() {
        super.onResume()
        dynamic_weather_view.onResume()
    }

    override fun onPause() {
        super.onPause()
        dynamic_weather_view.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        dynamic_weather_view.onDestroy()
    }

    /**
     * 显示动态天气
     */
    private fun showDynamicWeather(){
        val info = ShortWeatherInfo()
        info.sunrise = "06:00"
        info.sunset = "19:00"
        info.moonrise = "19:00"
        info.moonset = "06:00"
        val type = SunnyType(activity, info)
        dynamic_weather_view.setType(type)
    }
}