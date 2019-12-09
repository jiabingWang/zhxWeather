package com.zhx.weather

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.View
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.zhx.weather.base.BaseActivity
import com.zhx.weather.base.BaseFragment
import com.zhx.weather.base.MessageBus
import com.zhx.weather.common.MSG_LOCATION
import com.zhx.weather.fragment.WeatherFragment
import com.zhx.weather.util.myToast

/**
 * 主Activity
 */
class MainActivity : BaseActivity() {
    /**
     * 当前显示的Fg
     */
    private var mCurrentFg: BaseFragment? = null
    /**
     * 天气Fg
     */
    private var mWeatherFg: BaseFragment? = null
    /**
     * 高德定位SDK创建单次定位客户端
     */
    private var locationClientSingle: AMapLocationClient? = null

    override fun getLayoutResOrView() = R.layout.activity_main
    override fun initData() {
        getLocation()
    }

    override fun initUi(savedInstanceState: Bundle?) {
        setFragment()
    }

    override fun getClickView(): List<View?>? {
        return listOf()
    }

    override fun onSingleClick(view: View) {

    }

    private fun setFragment() {
        supportFragmentManager.beginTransaction().apply {
            if (mWeatherFg == null) {
                mWeatherFg = WeatherFragment.newInstance()
                add(R.id.fg_weather, mWeatherFg!!)
            }
            mCurrentFg = mWeatherFg
            mCurrentFg?.let {
                show(it)
            }
        }.commit()
    }

    /**
     * 获取当前位置
     */
    private fun getLocation() {
        getPermission(
            mutableListOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) { granted, checked ->
            if (granted) {
                startSingleLocation()
            } else {
                myToast("拒绝定位权限")
            }
        }
    }

    /**
     * 启动单次客户端定位
     */
    private fun startSingleLocation() {
        if (null == locationClientSingle) {
            locationClientSingle = AMapLocationClient(this@MainActivity)
        }
        val locationClientOption = AMapLocationClientOption()
        //使用单次定位
        locationClientOption.isOnceLocation = true
        // 地址信息
        locationClientOption.isNeedAddress = true
        locationClientOption.isLocationCacheEnable = false
        locationClientSingle?.setLocationOption(locationClientOption)
        locationClientSingle?.setLocationListener(locationSingleListener)
        locationClientSingle?.startLocation()
    }

    /**
     * 单次客户端的定位监听
     */
    private var locationSingleListener: AMapLocationListener = AMapLocationListener { location ->
        location?.let{
            Log.d("jiaBing", "定位结束${location.district}")
            var district = location.district
            if (district.isEmpty()) {
                district = "定位失败"
            }
            myToast("定位成功--$district")
            MessageBus.post(MSG_LOCATION, district)
        }
    }
}
