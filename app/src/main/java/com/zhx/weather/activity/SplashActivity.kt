package com.zhx.weather.activity

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.animation.*
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.zhx.weather.R
import com.zhx.weather.base.BaseActivity
import com.zhx.weather.base.MessageBus
import com.zhx.weather.common.MSG_LOGIN_SUCCESS
import com.zhx.weather.manager.BmobDataManager
import com.zhx.weather.manager.UserInfoManager
import com.zhx.weather.util.*
import kotlinx.android.synthetic.main.activity_splash_activity.*
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
        startAnimation()
    }

    override fun initListener() {
    }

    private fun startAnimation(){
        //旋转
        val ra = RotateAnimation(
            0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        ra.duration = 1000
        ra.fillAfter = true//动画保持状态
        //缩放
        val sa = ScaleAnimation(
            0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        sa.duration = 1000
        sa.fillAfter = true
        //渐变
        val aa = AlphaAnimation(0f, 1f)
        aa.duration = 2000
        aa.fillAfter = true
        //动画集合
        val set = AnimationSet(true)
        set.addAnimation(ra)
        set.addAnimation(sa)
        set.addAnimation(aa)
        //启动动画
        cl_splash.startAnimation(set)
        //设置动画监听
        set.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                val isSplash= spGet("isSplash",false)
                if (isSplash){
                    startActivity<MainActivity>()
                }else{
                    spSet("isSplash",true)
                    startActivity<GuideActivity>()
                }
                finish()
            }

            override fun onAnimationStart(animation: Animation?) {

            }

        })

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
                }
                UserInfoManager.INSTANCE.setAddressList(currentAddressList.toMutableSet())
            }
        }
    }

}
