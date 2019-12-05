package com.zhx.weather.fragment

import android.os.Bundle
import android.os.Message
import android.view.View
import com.zhx.weather.R
import com.zhx.weather.base.BaseFragment
import com.zhx.weather.base.MessageBus
import com.zhx.weather.bean.DynamicWeatherBean
import com.zhx.weather.bean.NowWeatherBean
import com.zhx.weather.common.MSG_WEATHER_TYPE_CHANGE
import com.zhx.weather.net.getWeatherForecast
import com.zhx.weather.net.getWeatherNow
import com.zhx.weather.util.myToast
import com.zhx.weather.util.textFrom
import kotlinx.android.synthetic.main.fragment_city_weather.*

/**
 * 描述：真正的显示天气Fg
 */
class CityWeatherFragment(private val cityName: String) :
    BaseFragment() {
    companion object {
        fun newInstance(cityName: String): CityWeatherFragment {
            return CityWeatherFragment(cityName)
        }
    }

    /**
     * 当前城市的天气状况Code
     */
    private var mCurrentDynamicWeatherBean: DynamicWeatherBean? = null

    fun getTitle(): String? {
        return cityName
    }

    override fun getLayoutResOrView() = R.layout.fragment_city_weather

    override fun initData() {
        getNowWeather()
    }

    override fun initUi(savedInstanceState: Bundle?) {

    }

    override fun getClickView(): List<View?>? {
        return listOf()
    }

    override fun onSingleClick(view: View) {

    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser){
            //发送消息，通知需要显示的天气动画
            mCurrentDynamicWeatherBean?.let {
                MessageBus.post(MSG_WEATHER_TYPE_CHANGE,it)
            }
        }
    }
    override fun onResume() {
        super.onResume()

    }

    override fun onPause() {
        super.onPause()

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun getNowWeather() {
        cityName.getWeatherNow(successCallback = { now ->
            showWeatherInfoNow(now.heWeather6[0].now)
            cityName.getWeatherForecast(successCallback = { forecast ->
                val today = forecast.heWeather6[0].daily_forecast[0]
                mCurrentDynamicWeatherBean = DynamicWeatherBean(
                    now.heWeather6[0].now.cond_code.toInt(),
                    today.sr,
                    today.ss,
                    today.mr,
                    today.ms
                )
            }, failCallback = {
                myToast("出现错误${it.message}")
            })
        }, failCallback = {
            myToast("出现错误${it.message}")
        })

    }

    private fun showWeatherInfoNow(now: NowWeatherBean.HeWeather6Bean.NowBean) {
        //当前温度
        tv_current_tmp textFrom "${now.tmp}°"
        //湿度
        tv_hum_value textFrom now.hum
        //气压
        tv_pres_value textFrom now.pres
        //风力
        tv_wind_sc_value textFrom "${now.wind_sc}级"
    }
}