package com.zhx.weather.listener

import android.os.Bundle
import android.view.View

/**
 * 描述：Activity,Fragment基类需要实现
 */
interface NormalInterface {
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
     * 初始化各个事件监听
     */
    fun initListener()
    /**
     * 获取用户的动态权限
     */
    fun getPermission(permission: MutableList<String>, needPermissionCall: (granted: Boolean,doNotRemindAgainCall:Boolean)->Unit)
}