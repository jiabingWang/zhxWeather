package com.zhx.weather.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.zhx.weather.R
import com.zhx.weather.adapter.ForecastAdapter
import com.zhx.weather.adapter.LifestyleAdapter
import com.zhx.weather.base.BaseFragment
import com.zhx.weather.base.MessageBus
import com.zhx.weather.bean.DynamicWeatherBean
import com.zhx.weather.bean.ForecastWeatherBean
import com.zhx.weather.bean.LifestyBean
import com.zhx.weather.bean.NowWeatherBean
import com.zhx.weather.common.MSG_SPEAK
import com.zhx.weather.common.MSG_WEATHER_TYPE_CHANGE
import com.zhx.weather.net.getLifestyle
import com.zhx.weather.net.getWeatherForecast
import com.zhx.weather.net.getWeatherNow
import com.zhx.weather.util.TTSManager
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
    private lateinit var mForecastAdapter: ForecastAdapter
    private var mForecastData =
        mutableListOf<ForecastWeatherBean.HeWeather6Bean.DailyForecastBean>()
    /**
     * 当前天气
     */
    private var mNowWeatherBean :NowWeatherBean.HeWeather6Bean?=null
    private lateinit var mLifestyleAdapter : LifestyleAdapter
    private var mLifestyleData =
        mutableListOf<LifestyBean.HeWeather6Bean.LifestyleBean>()
    fun getTitle(): String? {
        return cityName
    }

    override fun getLayoutResOrView() = R.layout.fragment_city_weather

    override fun initData() {
        getNowWeather()
        getLifestyle()
    }

    override fun initUi(savedInstanceState: Bundle?) {
        initForecastRv()
        initLifestyleRv()
        initRefreshLayout()
    }

    override fun initListener() {

    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            //发送消息，通知需要显示的天气动画
            mCurrentDynamicWeatherBean?.let {
                MessageBus.post(MSG_WEATHER_TYPE_CHANGE, it)
            }
        }
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onPause() {
        super.onPause()

    }

    override fun onMessageBus(code: Int, event: Any?) {
        super.onMessageBus(code, event)
        if (code == MSG_SPEAK && cityName ==event) {
            TTSManager.getInstance(activity, "xiaoyu", "30").speak(speak(), null)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
    }
    private fun initRefreshLayout() {
        refreshLayout.setEnableRefresh(true)
        refreshLayout.setEnableLoadMore(false)
        refreshLayout.setOnRefreshListener{
            initData()
        }
    }
    private fun getNowWeather() {
        //获取当前实时天气
        cityName.getWeatherNow(successCallback = { now ->
            mNowWeatherBean =now.heWeather6[0]
            showWeatherInfoNow(now.heWeather6[0].now)
            //获取3天内预报
            getForecastWeather(now.heWeather6[0].now.cond_code)
        }, failCallback = {
            myToast("出现错误${it.message}")
        })

    }
    private fun getForecastWeather(condCode: String) {
        cityName.getWeatherForecast(successCallback = { forecast ->
            val today = forecast.heWeather6[0].daily_forecast[0]
            mCurrentDynamicWeatherBean = DynamicWeatherBean(
                condCode.toInt(),
                today.sr,
                today.ss,
                today.mr,
                today.ms
            )
            MessageBus.post(MSG_WEATHER_TYPE_CHANGE, mCurrentDynamicWeatherBean)
            mForecastAdapter.data.clear()
            for (item in forecast.heWeather6[0].daily_forecast) {
                mForecastAdapter.data.add(item)
            }
            mForecastAdapter.notifyDataSetChanged()
        }, failCallback = {
            myToast("出现错误${it.message}")
        })
    }

    private fun getLifestyle(){
        cityName.getLifestyle(successCallback = {lifestyle ->
            mLifestyleAdapter.data.clear()
            for (item in lifestyle.heWeather6[0].lifestyle){
                mLifestyleAdapter.data.add(item)
            }
            mLifestyleAdapter.notifyDataSetChanged()
        },failCallback = {
            myToast("出现错误${it.message}")
        })
        refreshLayout.finishRefresh(1000)
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

    private fun initForecastRv() {
        mForecastAdapter = ForecastAdapter(activity!!, mForecastData)
        rv_forecast.layoutManager =GridLayoutManager(activity!!,3)
        rv_forecast.adapter = mForecastAdapter
    }
    private fun initLifestyleRv(){
        mLifestyleAdapter = LifestyleAdapter(activity!!,mLifestyleData)
        rv_lifestyle.layoutManager =GridLayoutManager(activity!!,4)
        rv_lifestyle.adapter = mLifestyleAdapter
    }
    private fun speak():String?{
        var m:String?=null
        mNowWeatherBean?.let {
            val message = StringBuffer()
            message.append("${getString(R.string.app_name)}为您播报：")
            message.append("\r\n")
            message.append(cityName)
            message.append("天气：")
            message.append("\r\n")
            message.append(it.update.loc)
            message.append(" 发布：")
            message.append("\r\n")
            message.append(it.now.cond_txt)
            message.append("，")
            message.append("当前温度：")
            message.append(it.now.tmp).append("℃")
            message.append("。")
            message.append("。")
            message.append("\r\n")
            message.append("明天：")
            message.append(mForecastData[1].cond_txt_d)
            message.append("，")
            message.append(mForecastData[1].tmp_min).append("℃-")
            message.append(mForecastData[1].tmp_max).append("℃")
            message.append("。")
            m =message.toString()
        }
        return m
    }
}