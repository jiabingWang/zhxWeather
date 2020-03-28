package com.zhx.weather.activity

import android.app.AlertDialog
import android.os.Bundle
import com.zhx.weather.R
import com.zhx.weather.base.BaseActivity
import com.zhx.weather.base.MessageBus
import com.zhx.weather.common.MSG_LOGIN_OUT
import com.zhx.weather.manager.UserInfoManager
import com.zhx.weather.util.TTSManager
import com.zhx.weather.util.isStatusBarBlackTextColor
import kotlinx.android.synthetic.main.activity_setting.*
import org.jetbrains.anko.toast

class SettingActivity : BaseActivity() {
    override fun getLayoutResOrView(): Int =R.layout.activity_setting

    override fun initData() {
    }

    override fun initUi(savedInstanceState: Bundle?) {
        isStatusBarBlackTextColor(true)
    }

    override fun initListener() {
        titleBar.leftView.setOnClickListener {
            finish()
        }
        rl_share_app.setOnClickListener {

        }
        rl_set_sound.setOnClickListener {
            if (UserInfoManager.INSTANCE.isLogin()){
                toast("请先登录")
            }else{
                setVoiceName()
            }
        }
        rl_login_out.setOnClickListener {
            if (UserInfoManager.INSTANCE.isLogin()){
                val dialog =AlertDialog.Builder(this)
                dialog.setTitle("提示")
                dialog.setMessage("是否退出登录")
                dialog.setNegativeButton("取消") { dialog, which -> dialog.dismiss() }
                dialog.setPositiveButton("确定") { dialog, which ->
                    UserInfoManager.INSTANCE.cleanUserInfo()
                    //退出登录
                    MessageBus.post(MSG_LOGIN_OUT,null)
                    toast("退出成功")
                    dialog.dismiss()
                }
                dialog.show()
            }else{
                toast("当前未登录")
            }

        }
    }
    private fun setVoiceName() {
        var voiceName = "xiaoyu"
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setIcon(android.R.drawable.ic_dialog_info)
        builder.setTitle("请选择发音人")
        val items = arrayOf(
            "小燕(女普通话)",
            "小宇(女普通话)",
            "小梅(粤语)",
            "小蓉(四川话)",
            "小倩(东北话)",
            "小坤(河南话)",
            "小强(湖南话)",
            "小莹(陕西话)",
            "小新(童年男声)",
            "楠楠(童年女声)",
            "老孙(老年男声)"
        )
        builder.setSingleChoiceItems(
            items, -1
        ) { dialog, which ->
            //which指的是用户选择的条目的下标
            //dialog:触发这个方法的对话框
            //                Toast.makeText(WeatherActivity.this, "您选择的是:"+items[which], Toast.LENGTH_SHORT).show();
            when (which) {
                0 -> voiceName = "xiaoyan"
                1 -> voiceName = "xiaoyu"
                2 -> voiceName = "xiaomei"
                3 -> voiceName = "xiaorong"
                4 -> voiceName = "xiaoqian"
                5 -> voiceName = "xiaokun"
                6 -> voiceName = "xiaoqiang"
                7 -> voiceName = "vixying"
                8 -> voiceName = "xiaoxin"
                9 -> voiceName = "nannan"
                10 -> voiceName = "vils"
                else -> {
                }
            }
            UserInfoManager.INSTANCE.setVoiceName(voiceName)
            dialog.dismiss()
            TTSManager.destroy()//重新选择声源后需要停止当前播报，并且为下一次能重新设置生源准备。这点很重要！！
        }
        builder.show()
    }
}
