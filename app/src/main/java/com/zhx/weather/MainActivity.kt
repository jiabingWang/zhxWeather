package com.zhx.weather

import android.os.Bundle
import android.view.View
import com.zhx.weather.base.BaseActivity
import com.zhx.weather.base.BaseFragment
import com.zhx.weather.fragment.WeatherFragment

/**
 * 主Activity
 */
class MainActivity : BaseActivity() {
    /**
     * 当前显示的Fg
     */
    private var mCurrentFg :BaseFragment?=null
    /**
     * 天气Fg
     */
    private var mWeatherFg :BaseFragment?=null
    override fun getLayoutResOrView() = R.layout.activity_main
    override fun initData() {

    }

    override fun initUi(savedInstanceState: Bundle?) {
        setFragment()
    }

    override fun getClickView(): List<View?>? {
        return listOf()
    }

    override fun onSingleClick(view: View) {

    }
    private fun setFragment(){
        supportFragmentManager.beginTransaction().apply {
            if (mWeatherFg == null){
                mWeatherFg = WeatherFragment.newInstance()
                add(R.id.fg_weather,mWeatherFg!!)
            }
            mCurrentFg = mWeatherFg
            mCurrentFg?.let {
                show(it)
            }
        }.commit()
    }
}
