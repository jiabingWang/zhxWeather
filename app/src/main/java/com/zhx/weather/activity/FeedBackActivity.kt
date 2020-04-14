package com.zhx.weather.activity

import android.os.Bundle
import com.zhx.weather.R
import com.zhx.weather.base.BaseActivity
import com.zhx.weather.manager.BmobDataManager
import com.zhx.weather.util.isStatusBarBlackTextColor
import kotlinx.android.synthetic.main.activity_feed_back.*
import org.jetbrains.anko.toast

class FeedBackActivity : BaseActivity() {
    override fun getLayoutResOrView(): Int = R.layout.activity_feed_back

    override fun initData() {
    }

    override fun initUi(savedInstanceState: Bundle?) {
        isStatusBarBlackTextColor(true)
    }

    override fun initListener() {
        titleBar.leftView.setOnClickListener {
            finish()
        }
        tv_apply.setOnClickListener {
            if (et_feed_back.text.isNullOrEmpty()){
                toast("请填写意见反馈")
            }else{
                BmobDataManager.INSTANCE.addFeedBack(et_feed_back.text.toString(),{
                    toast("提交成功")
                }){

                }
            }
        }
    }
}
