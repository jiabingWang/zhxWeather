package com.zhx.weather.listener

import com.zhx.weather.common.IS_DEBUG
import com.zhx.weather.util.logI

/**
 * 描述：简单的应用内消息通讯
 */
interface MessageBusInterface {
    /**
     * activity消息通讯
     */
    fun onMessageBus(code: Int,event: Any?) {
        if (IS_DEBUG) {
            logI("onMessageBus-->{code:$code event:$event}\n")
        }
    }
}