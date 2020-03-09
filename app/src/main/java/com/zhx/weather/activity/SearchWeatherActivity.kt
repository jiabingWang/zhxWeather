package com.zhx.weather.activity

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.smarttop.library.bean.City
import com.smarttop.library.bean.County
import com.smarttop.library.bean.Province
import com.smarttop.library.bean.Street
import com.smarttop.library.widget.AddressSelector
import com.smarttop.library.widget.BottomDialog
import com.smarttop.library.widget.OnAddressSelectedListener
import com.zhx.weather.R
import com.zhx.weather.adapter.ForecastAdapter
import com.zhx.weather.base.BaseActivity
import com.zhx.weather.bean.ForecastWeatherBean
import com.zhx.weather.bean.NowWeatherBean
import com.zhx.weather.net.getWeatherForecast
import com.zhx.weather.net.getWeatherNow
import com.zhx.weather.util.isStatusBarBlackTextColor
import com.zhx.weather.util.myToast
import com.zhx.weather.util.textFrom
import kotlinx.android.synthetic.main.activity_search_weather.*
import kotlinx.android.synthetic.main.activity_search_weather.rv_forecast
import kotlinx.android.synthetic.main.activity_search_weather.tv_hum_value
import kotlinx.android.synthetic.main.activity_search_weather.tv_pres_value
import kotlinx.android.synthetic.main.activity_search_weather.tv_wind_sc_value
import org.jetbrains.anko.toast

class SearchWeatherActivity : BaseActivity() , OnAddressSelectedListener,
    AddressSelector.OnDialogCloseListener {


    private var mAddressDialog: BottomDialog? = null
    private lateinit var mForecastAdapter: ForecastAdapter
    private var mForecastData =
        mutableListOf<ForecastWeatherBean.HeWeather6Bean.DailyForecastBean>()
    override fun getLayoutResOrView(): Int =R.layout.activity_search_weather

    override fun initData() {
    }

    override fun initUi(savedInstanceState: Bundle?) {
        isStatusBarBlackTextColor(true)
        initForecastRv()
        initAddressDialog()
    }

    override fun initListener() {
        titleBar.leftView.setOnClickListener {
            finish()
        }
        tv_search_city.setOnClickListener {
            mAddressDialog?.show()
        }
    }
    private fun initForecastRv() {
        mForecastAdapter = ForecastAdapter(this, mForecastData)
        rv_forecast.layoutManager = GridLayoutManager(this,3)
        rv_forecast.adapter = mForecastAdapter
    }
    private fun getNowWeather(cityName: String) {
        //获取当前实时天气
        cityName.getWeatherNow(successCallback = { now ->
            showWeatherInfoNow(now.heWeather6[0].now)
            //获取3天内预报
            getForecastWeather(cityName)
        }, failCallback = {
            myToast("出现错误${it.message}")
        })

    }
    private fun getForecastWeather(cityName: String) {
        cityName.getWeatherForecast(successCallback = { forecast ->
            mForecastAdapter.data.clear()
            for (item in forecast.heWeather6[0].daily_forecast) {
                mForecastAdapter.data.add(item)
            }
            mForecastAdapter.notifyDataSetChanged()
        }, failCallback = {
            myToast("出现错误${it.message}")
        })
    }
    private fun showWeatherInfoNow(now: NowWeatherBean.HeWeather6Bean.NowBean) {
        //湿度
        tv_hum_value textFrom now.hum
        //气压
        tv_pres_value textFrom now.pres
        //风力
        tv_wind_sc_value textFrom "${now.wind_sc}级"
    }
    private fun initAddressDialog() {
        mAddressDialog = BottomDialog(this)
        mAddressDialog?.let {
            it.setOnAddressSelectedListener(this)
            it.setDialogDismisListener(this)
            it.setTextSize(14f)//设置字体的大小
            it.setIndicatorBackgroundColor(android.R.color.holo_orange_light)//设置指示器的颜色
            it.setTextSelectedColor(android.R.color.holo_orange_light)//设置字体获得焦点的颜色
            it.setTextUnSelectedColor(android.R.color.holo_blue_light)//设置字体没有获得焦点的颜色
        }
    }
    override fun onAddressSelected(
        province: Province?,
        city: City?,
        county: County?,
        street: Street?
    ) {
        Log.d("jiaBing","province${province?.name}--city${city?.name}--county${county?.name}")
        var address = ""
        if(province?.name =="香港"||province?.name =="台湾"||province?.name =="澳门"||province?.name =="钓鱼岛"){
            toast("该地区暂不支持")
            mAddressDialog?.dismiss()
            return
        }
        if (province?.name =="北京"||province?.name =="上海"||province?.name =="天津"){
            address = city!!.name
        }else{
            address = county!!.name
        }
        getNowWeather(address)
        tv_search_city.text = address
        mAddressDialog?.dismiss()
    }

    override fun dialogclose() {
        mAddressDialog?.dismiss()
    }
}
