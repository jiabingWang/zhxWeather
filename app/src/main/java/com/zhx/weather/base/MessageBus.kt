package com.zhx.weather.base

import com.zhx.weather.listener.MessageBusInterface


/**
 * 描述：利用map和接口相关实现的轻量级应用内消息总线机制
 */
object MessageBus {
    /**
     * 消息接口集合
     */
    private val receiverList = mutableListOf<MessageBusInterface>()
    /**
     * 最近一次的值
     */
    private var _lastValue: Any? = null
    /**
     * 最近一次的code
     */
    private var _lastCode: Int? = null

    val lastValue: Any?
        get() = _lastValue

    val lastCode: Int?
        get() = _lastCode

    /**
     * 注册
     * 新注册的，会去查看当前是否有没发送的消息，有的话，自己发送给自己，之后清除
     */
    fun register(receiver: MessageBusInterface) {
        if (!receiverList.contains(receiver)) {
            receiverList.add(receiver)
            val code = lastCode
            val value = lastValue
            if (code != null) {
                receiver.onMessageBus(code,value)
                clearLastValue()
            }
        }
    }

    /**
     * 清除数据防止内存泄漏
     */
    private fun clearLastValue() {
        _lastValue = null
        _lastCode = null
    }

    /**
     * 移除注册
     */
    fun unRegister(receiver: MessageBusInterface) {
        if (receiverList.contains(receiver)) {
            receiverList.remove(receiver)
        }
    }

    /**
     * 将值发送出去
     * isSticky 新注册的第一个接口是否接受此数据
     */
    fun post(code: Int, event: Any?, isSticky: Boolean = false) {
        if (isSticky) {
            postLastValue(code, event)
        } else {
            clearLastValue()
        }
        receiverList.forEach {
            it.onMessageBus(code,event )
        }
    }

    /**
     * 赋值最新的一次值
     * 每次新添加的监听者，都可以接受到最新的一次值
     */
    fun postLastValue(code: Int, event: Any?) {
        _lastValue = event
        _lastCode = code
    }

    /**
     * 将值发送出去
     *  isSticky 新注册的第一个接口是否接受此数据
     *  @mark 哪些类需要监听这个消息
     */
    fun post(code: Int, event: Any?, isSticky: Boolean = false, vararg mark: Class<*>) {
        if (isSticky) {
            postLastValue(code, event)
        } else {
            clearLastValue()
        }
        if (mark.count() == 0) {
            receiverList.forEach {
                it.onMessageBus(code,event)
            }
        } else {
            receiverList.forEach {
                mark.forEach { cls ->
                    if (it.javaClass == cls) {
                        it.onMessageBus(code,event)
                    }
                }
            }
        }
    }
}