package com.zhx.weather.fragment

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.zhx.weather.R
import com.zhx.weather.base.BaseFragment
import com.zhx.weather.bean.DynamicWeatherBean
import com.zhx.weather.common.MSG_WEATHER_TYPE_CHANGE
import com.zhx.weather.dynamic.*
import com.zhx.weather.other.WeatherPageTransformer
import kotlinx.android.synthetic.main.fragment_weather.*
import java.util.ArrayList

/**
 * 描述：显示天气Fg的承载Fg
 */
class WeatherFragment : BaseFragment() {
    companion object {
        fun newInstance(): WeatherFragment {
            return WeatherFragment()
        }
    }

    private var mCityTitleList = listOf("余江", "宝山区")
    private lateinit var mVpAdapter: CityWeatherViewPagerAdapter
    private val mCityFgList = ArrayList<CityWeatherFragment>()
    override fun getLayoutResOrView() = R.layout.fragment_weather

    override fun initData() {

    }

    override fun initUi(savedInstanceState: Bundle?) {
        initVP()
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

    override fun onMessageBus(code: Int, event: Any?) {
        super.onMessageBus(code, event)
        if (code == MSG_WEATHER_TYPE_CHANGE){
            event?.let {

                showDynamicWeather(it as DynamicWeatherBean)
            }
        }
    }
    private fun initVP() {
        for (fg in mCityTitleList) {
            mCityFgList.add(CityWeatherFragment.newInstance(fg))
        }
        mVpAdapter = CityWeatherViewPagerAdapter(childFragmentManager, mCityFgList)
        vp_city_weather.setPageTransformer(true, WeatherPageTransformer())
        vp_city_weather.offscreenPageLimit = mCityTitleList.size
        vp_city_weather.adapter = mVpAdapter
        vp_indicator.setViewPager(vp_city_weather)
        vp_city_weather.currentItem = 0
    }

    internal inner class CityWeatherViewPagerAdapter(
        fgManager: FragmentManager,
        private val fgList: ArrayList<CityWeatherFragment>
    ) : FragmentPagerAdapter(fgManager) {
        override fun getItem(position: Int): Fragment {
            return fgList[position]
        }

        override fun getCount(): Int {
            return fgList.size
        }

        override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
//            super.destroyItem(container, position, `object`)
        }

        //自定义的SimplePagerIndicator 会获取Title,所以需要重写该方法
        override fun getPageTitle(position: Int): CharSequence? {
            return fgList[position].getTitle()
        }
    }

    /**
     * 显示动态天气
     * tip ：接口返回字段为condCode，代码中命名为weatherCode。代表的意思都是天气状况代码
     */
    private fun showDynamicWeather(data: DynamicWeatherBean) {

        val info = ShortWeatherInfo()
        info.sunrise = data.sunrise
        info.sunset = data.sunset
        info.moonrise = data.moonrise
        info.moonset =data.moonset
        val type: BaseWeatherType
        when (weatherCodeToWeatherType(data.weatherCode)) {
            WeatherType.TYPE_SUNNY -> {
                val sunnyType = SunnyType(activity, info)
                type = sunnyType
            }
            WeatherType.TYPE_CLOUD -> {
                val sunnyType = SunnyType(activity, info)
                sunnyType.isCloud = true
                type = sunnyType
            }
            WeatherType.TYPE_OVERCAST -> {
                type = OvercastType(activity, info)
            }
            WeatherType.TYPE_RAIN_SMALL -> {
                val rainType = RainType(activity, RainType.RAIN_LEVEL_1, RainType.WIND_LEVEL_1)
                type = rainType
            }
            WeatherType.TYPE_RAIN_MIDDLE -> {
                val rainType = RainType(activity, RainType.RAIN_LEVEL_2, RainType.WIND_LEVEL_2)
                type = rainType
            }
            WeatherType.TYPE_RAIN_BIG -> {
                val rainType = RainType(activity, RainType.RAIN_LEVEL_3, RainType.WIND_LEVEL_3)
                type = rainType
            }
            WeatherType.TYPE_RAIN_SNOW -> {
                val rainType = RainType(activity, RainType.RAIN_LEVEL_1, RainType.WIND_LEVEL_1)
                rainType.isSnowing = true
                type = rainType
            }
            WeatherType.TYPE_SNOW_SMALL -> {
                val snowType = SnowType(activity, SnowType.SNOW_LEVEL_1)
                type = snowType
            }
            WeatherType.TYPE_SNOW_MIDDLE -> {
                val snowType = SnowType(activity, SnowType.SNOW_LEVEL_2)
                type = snowType
            }
            WeatherType.TYPE_SNOW_BIG -> {
                val snowType = SnowType(activity, SnowType.SNOW_LEVEL_3)
                type = snowType
            }
            WeatherType.TYPE_HAIL -> {
                type = HailType(activity)
            }
            WeatherType.TYPE_FOG -> {
                type = FogType(activity)
            }
            WeatherType.TYPE_HAZE -> {
                type = HazeType(activity)
            }
            WeatherType.TYPE_SANDSTORM -> {
                type = SandstormType(activity)
            }
        }

        dynamic_weather_view.setType(type)
    }

    /**
     * @param condCode 和风天气返回的code
     * 详细code对应天气链接 https://dev.heweather.com/docs/refer/condition
     * 分的过于细致，但是对于天气动画就那么几个，所以进行范围归类
     */
    private fun weatherCodeToWeatherType(condCode: Int): WeatherType {
        when (condCode) {
            100, 200, 201, 202, 203, 204, 205 -> return WeatherType.TYPE_SUNNY
            101, 102, 103 -> return WeatherType.TYPE_CLOUD
            104, 206, 207, 208 -> return WeatherType.TYPE_OVERCAST
            300, 305, 309, 314 -> return WeatherType.TYPE_RAIN_SMALL
            301, 306315, 399 -> return WeatherType.TYPE_RAIN_MIDDLE
            302, 303, 307, 308, 310, 311, 312, 313, 316, 317, 318 -> return WeatherType.TYPE_RAIN_BIG
            404, 405, 406 -> return WeatherType.TYPE_RAIN_SNOW
            400, 407 -> return WeatherType.TYPE_SNOW_SMALL
            401, 408, 409, 499 -> return WeatherType.TYPE_SNOW_MIDDLE
            402, 403, 410 -> return WeatherType.TYPE_SNOW_BIG
            304 -> return WeatherType.TYPE_HAIL
            500, 501, 509, 510, 514, 515 -> return WeatherType.TYPE_FOG
            502, 511, 512, 513 -> return WeatherType.TYPE_HAZE
            209, 210, 211, 212, 213, 503, 504, 507, 508 -> return WeatherType.TYPE_SANDSTORM
            else -> return WeatherType.TYPE_SUNNY
        }
    }

    /**
     * 天气类型
     * TYPE_SUNNY 晴
     * TYPE_CLOUD 多云
     * TYPE_OVERCAST 阴
     * TYPE_RAIN_SMALL 小雨
     * TYPE_RAIN_MIDDLE 中雨
     * TYPE_RAIN_BIG 大雨
     * TYPE_RAIN_SNOW 雨夹雪
     * TYPE_SNOW_SMALL 小雪
     * TYPE_SNOW_MIDDLE 中雪
     * TYPE_SNOW_BIG 大雪
     * TYPE_HAIL 冰雹
     * TYPE_FOG 雾
     * TYPE_HAZE 雾霾
     * TYPE_SANDSTORM 沙尘暴
     */
    enum class WeatherType {
        TYPE_SUNNY,
        TYPE_CLOUD,
        TYPE_OVERCAST,
        TYPE_RAIN_SMALL,
        TYPE_RAIN_MIDDLE,
        TYPE_RAIN_BIG,
        TYPE_RAIN_SNOW,
        TYPE_SNOW_SMALL,
        TYPE_SNOW_MIDDLE,
        TYPE_SNOW_BIG,
        TYPE_HAIL,
        TYPE_FOG,
        TYPE_HAZE,
        TYPE_SANDSTORM
    }
}