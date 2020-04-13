package com.zhx.weather.bean

import cn.bmob.v3.BmobObject

/**
 * 描述：用户表
 */
data class UserBean(val phone :String, val name :String) :BmobObject()