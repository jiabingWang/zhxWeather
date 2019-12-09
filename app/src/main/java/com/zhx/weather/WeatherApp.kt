package com.zhx.weather

import android.app.Application
import com.scwang.smartrefresh.header.BezierCircleHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.zhx.weather.net.initOkHttp

/**
 * 路漫漫其修远兮，吾将上下而求索
 * @author 服装学院的IT男
 * 时间: on 2019/11/7
 * 包名 com.wjb.momweather
 * 描述：
 */
class WeatherApp :Application() {
    companion object{
        lateinit var app: Application
    }
    override fun onCreate() {
        super.onCreate()
        app = this
        initOkHttp()
//        DoraemonKit.install(this)
        //全局下拉格式
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, _ ->
            BezierCircleHeader(context)
        }
    }
}