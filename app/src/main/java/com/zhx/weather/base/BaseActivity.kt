package com.zhx.weather.base

import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.umeng.analytics.MobclickAgent
import com.zhx.weather.listener.MessageBusInterface
import com.zhx.weather.listener.NormalInterface
import com.zhx.weather.util.ScreenAdaptation


/**
 * 描述：Activity基类
 * 1.权限请求
 * 2.布局
 * 3.屏幕适配
 * 4.消息总线（轻量级）
 * 5.初始化数据
 * 6.初始化UI
 * 7.点击事件（需要先获取需要点击的View）
 */
abstract class BaseActivity : AppCompatActivity(), MessageBusInterface, NormalInterface {

    /**动态权限请求码*/
    private val needPermissionCode = 1024
    /**
     * 动态权限的回调，是否有此权限
     * 动态权限的回调，是否勾选了不再提示
     */
    private var needPermissionCall: ((granted: Boolean, checked: Boolean) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        //开启屏幕适配
        ScreenAdaptation.setCustomDensity(this, application)
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResOrView())
        if (Build.VERSION.SDK_INT >= 21) {
            val decorView = window.decorView
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.statusBarColor = Color.TRANSPARENT
        }
//        setTranslucentStatus()
//        isStatusBarBlackTextColor(true)
        //消息总线注册
        MessageBus.register(this)
        initUi(savedInstanceState)


        initData()
        initListener()
    }



    override fun getPermission(
        permission: MutableList<String>,
        needPermissionCall: (granted: Boolean, checked: Boolean) -> Unit
    ) {
        var isNeedRequest = false
        val list = mutableListOf<String>()
        permission.forEach {
            if (ActivityCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED) {
                isNeedRequest = true
                list.add(it)
            }
        }
        if (isNeedRequest) {
            this.needPermissionCall = needPermissionCall
            if (list.isNotEmpty()) {
                ActivityCompat.requestPermissions(this, list.toTypedArray(), needPermissionCode)
            } else {
                needPermissionCall.invoke(true, false)
            }
        } else {
            needPermissionCall.invoke(true, false)
        }
    }

    /**
     * 处理请求的权限结果
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == needPermissionCode) {
            var isGetAllPermission = true
            var checked = false
            for (i in 0 until grantResults.size) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    isGetAllPermission = false
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            permissions[i]
                        )
                    ) {
                        checked = true
                    }
                }
            }
            needPermissionCall?.invoke(isGetAllPermission, checked)
        }
    }

    override fun onResume() {
        //是否开启屏幕适配,横竖屏切换，会导致之前设置失效，这里重新设置
        ScreenAdaptation.setCustomDensity(this, application)
        super.onResume()
        MobclickAgent.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        MobclickAgent.onPause(this)
    }

    override fun onDestroy() {
        //消息总线解绑
        MessageBus.unRegister(this)
        super.onDestroy()
    }

    /**
     * 沉浸式状态栏
     */
    private fun setTranslucentStatus() {
        val lp = window.attributes
        val flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        lp.flags = flagTranslucentStatus
        window.attributes = lp
    }
}
