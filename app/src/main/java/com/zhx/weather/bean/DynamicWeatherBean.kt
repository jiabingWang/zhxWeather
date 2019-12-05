package com.zhx.weather.bean

/**
 * 描述：动画天气控件需要显示的数据
 * @param weatherCode 天气代码
 * @param sunrise 日出时间
 * @param sunset 日落时间
 * @param moonrise 月出时间
 * @param moonset 月落时间
 */
data class DynamicWeatherBean (val weatherCode:Int,val sunrise:String,val sunset :String,val  moonrise :String,val moonset :String)