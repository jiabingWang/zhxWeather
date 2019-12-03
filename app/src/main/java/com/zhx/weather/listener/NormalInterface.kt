package com.zhx.weather.listener

import android.os.Bundle
import android.view.View

/**
 * 描述：Activity,Fragment基类需要实现
 */
interface NormalInterface : OnSingleClickListener {
    /**
     * 获取布局资源
     */
    fun getLayoutResOrView(): Int
    /**
     * 初始化数据
     */
    fun initData()
    /**
     * 初始化view
     */
    fun initUi(savedInstanceState: Bundle?)
    /**
     * 获取需要注册点击的view
     */
    fun getClickView(): List<View?>?
    /**
     * 获取用户的动态权限
     */
    fun getPermission(permission: MutableList<String>, needPermissionCall: (granted: Boolean,doNotRemindAgainCall:Boolean)->Unit)
}