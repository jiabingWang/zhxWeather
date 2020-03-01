package com.zhx.weather.manager

import android.text.TextUtils
import com.google.gson.Gson
import com.zhx.weather.WeatherApp
import com.zhx.weather.bean.AddressBean
import com.zhx.weather.util.spGet
import com.zhx.weather.util.spSet


/**
 * 描述：用户本地信息
 */
class UserInfoManager {
    companion object {
        val INSTANCE: UserInfoManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            UserInfoManager()
        }
    }

    /**
     * 设置UID
     */
    fun setUserId(uid: String) {
        return WeatherApp.app.spSet("uid", uid)
    }

    /**
     * 获取UID
     */
    fun getUserId(): String? {
         return WeatherApp.app.spGet("uid", "")
    }

    /**
     * 获取地址列表
     */
    fun getAddressList(): MutableSet<String> {
        return WeatherApp.app.spGet<MutableSet<String>>("address", mutableSetOf())
    }
    /**
     * 设置地址列表
     */
    fun setAddressList(data :MutableSet<String>){
        WeatherApp.app.spSet("address",data)
    }
    fun isLogin(): Boolean {
        return !TextUtils.isEmpty(getUserId())
    }

    fun isNotLogin():Boolean {
        return !isLogin()
    }



    //清空用户信息
    fun cleanUserInfo() {
        setUserId("")
        return WeatherApp.app.spSet("userInfo","")
    }

}