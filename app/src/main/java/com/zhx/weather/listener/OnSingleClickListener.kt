package com.zhx.weather.listener

import android.view.View

/**
 * 描述：防止快速点击
 */
interface OnSingleClickListener : View.OnClickListener{
    companion object {
        /**
         * 上一次按钮点击时间
         */
        internal var lastClickTime = 0L
    }
    /**
     * 设置快速点击的间隔
     * 默认300ms只处理一次点击
     */
    fun getDoubleClickSpace() = 300L

    /**
     * 点击事件监听
     */
    fun onSingleClick(view: View)
    /**
     * 统一处理快速点击的情况
     */
    override fun onClick(v: View?) {
        if (System.currentTimeMillis() - lastClickTime < getDoubleClickSpace()) {
            return
        }
        lastClickTime = System.currentTimeMillis()
        v?.let {
            onSingleClick(it)
        }
    }
}