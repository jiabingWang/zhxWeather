package com.zhx.weather.activity

import android.graphics.Color
import android.os.Bundle
import androidx.annotation.ColorRes
import com.zhx.weather.R
import com.zhx.weather.base.BaseActivity
import com.zhx.weather.util.isStatusBarBlackTextColor
import com.zhx.weather.util.setStatusBarColor
import kotlinx.android.synthetic.main.activity_about_us.*

class AboutUsActivity : BaseActivity() {
    override fun getLayoutResOrView(): Int =R.layout.activity_about_us

    override fun initData() {
    }

    override fun initUi(savedInstanceState: Bundle?) {
        isStatusBarBlackTextColor(true)
    }

    override fun initListener() {
        titleBar.leftView.setOnClickListener {
            finish()
        }
    }

}
