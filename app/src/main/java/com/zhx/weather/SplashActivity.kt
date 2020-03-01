package com.zhx.weather

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.google.gson.Gson
import com.zhx.weather.base.BaseActivity
import com.zhx.weather.base.MessageBus
import com.zhx.weather.bean.AddressBean
import com.zhx.weather.common.MSG_LOCATION
import com.zhx.weather.common.MSG_REFRESH_ADDRESS
import com.zhx.weather.manager.UserInfoManager
import com.zhx.weather.util.myToast
import com.zhx.weather.util.spGet
import com.zhx.weather.util.spSet
import org.jetbrains.anko.startActivity

class SplashActivity : BaseActivity() {
    /**
     * 高德定位SDK创建单次定位客户端
     */
    private var locationClientSingle: AMapLocationClient? = null

    override fun getLayoutResOrView(): Int = R.layout.activity_splash_activity

    override fun initData() {
        getLocation()
    }

    override fun initUi(savedInstanceState: Bundle?) {
    }

    override fun initListener() {
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
            locationClientSingle = AMapLocationClient(this@SplashActivity)
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
        location?.let {
            Log.d("jiaBing", "定位结束${location.district}")
            var district = location.district
            if (district.isEmpty()) {
                district = "定位失败"
            }else{
                val currentAddressList= UserInfoManager.INSTANCE.getAddressList().toMutableList()
                if (currentAddressList.size==0){
                    //第一次打开没地址信息
                    currentAddressList.add(district)
                }else{
                    //有地址信息但是第一个不是当前（换地方了）
//                    if (currentAddressList[0]!=district){
//                        currentAddressList.removeAt(0)
//                        currentAddressList.add(district)
//                    }
                }
                UserInfoManager.INSTANCE.setAddressList(currentAddressList.toMutableSet())
                startActivity<MainActivity>()
            }
        }
    }

}
