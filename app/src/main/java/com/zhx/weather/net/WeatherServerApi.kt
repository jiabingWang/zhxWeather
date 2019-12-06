package com.zhx.weather.net

import android.util.ArrayMap
import com.zhx.weather.WeatherApp
import com.zhx.weather.bean.ForecastWeatherBean
import com.zhx.weather.bean.LifestyBean
import com.zhx.weather.bean.NowWeatherBean
import org.jetbrains.anko.runOnUiThread

/**
 * 描述：网络请求相关
 */
//接口地址https://dev.heweather.com/docs/app/

/**余江县城市ID*/
const val CID_YU_JIANG = "CN101241102"
/**宝山区城市ID*/
const val CID_BAO_SAN = "CN101020300"
/**杨浦区城市ID*/
const val CID_YANG_PU = "CN101021700"
const val KEY = "b61c107f98d547ebab8c04472fd05413"
/**和风天气URL*/
const val BASE_URL = "https://free-api.heweather.net/s6/"

/**
 * 获取当前城市天气
 */
fun  String.getWeatherNow(successCallback: (NowWeatherBean) -> Unit,failCallback: (Throwable) -> Unit) {
    val params = ArrayMap<String, String>()
    params["location"] = this
    params["key"] = KEY
    "${BASE_URL}weather/now?".httpPost(params, NowWeatherBean::class.java, success = {
        WeatherApp.app.runOnUiThread {
            successCallback.invoke(it)
        }
    }, fail = {
        WeatherApp.app.runOnUiThread {
            failCallback.invoke(it)
        }
    })
}
/**
 * 获取3天的天气预报
 */
fun String.getWeatherForecast(successCallback: (ForecastWeatherBean) -> Unit, failCallback: (Throwable) -> Unit){
    val params = ArrayMap<String, String>()
    params["location"] = this
    params["key"] = KEY
    "${BASE_URL}weather/forecast?".httpPost(params, ForecastWeatherBean::class.java, success = {
        WeatherApp.app.runOnUiThread {
            successCallback.invoke(it)
        }
    }, fail = {
        WeatherApp.app.runOnUiThread {
            failCallback.invoke(it)
        }
    })
}
/**
 * 获取当前生活指数
 */
fun String.getLifestyle(successCallback: (LifestyBean) -> Unit, failCallback: (Throwable) -> Unit){
    val params = ArrayMap<String, String>()
    params["location"] = this
    params["key"] = KEY
    "${BASE_URL}weather/lifestyle?".httpPost(params, LifestyBean::class.java, success = {
        WeatherApp.app.runOnUiThread {
            successCallback.invoke(it)
        }
    }, fail = {
        WeatherApp.app.runOnUiThread {
            failCallback.invoke(it)
        }
    })
}