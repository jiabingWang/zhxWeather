package com.zhx.weather.common

import com.zhx.weather.BuildConfig

/**
 * 路漫漫其修远兮，吾将上下而求索
 * @author 服装学院的IT男
 * 时间: on 2019/11/7
 * 包名 com.wjb.momweather.common
 * 描述：
 */
/**
 * 是否是debug模式
 */
var IS_DEBUG = BuildConfig.DEBUG

//messageBus
//改变天气type
const val MSG_WEATHER_TYPE_CHANGE = 0x0010
const val MSG_LOCATION= 0x0011
const val MSG_LOGIN_SUCCESS= 0x0012
const val MSG_REFRESH_ADDRESS= 0x0013
/**高德Key*/
const val GAODE_KEY ="1e3bcc75b901899c38bfba1ec4cc448a"