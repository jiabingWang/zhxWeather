package com.zhx.weather.manager

import android.util.Log
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.SaveListener
import com.zhx.weather.bean.FeedBackBean
import com.zhx.weather.bean.UserBean

/**
 * 描述：Bmob数据类操作统一管理
 */
class BmobDataManager {
    companion object {
        val INSTANCE: BmobDataManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            BmobDataManager()
        }
    }

    /**
     * 注册用户
     * @param addSuccessCallback 成功回调
     * @param addFailCallback    失败回调
     */
    fun addUser(
        userBean: UserBean,
        addSuccessCallback: (objectId: String) -> Unit,
        addFailCallback: (objectId: BmobException) -> Unit
    ) {
        userBean.save(object : SaveListener<String>() {
            override fun done(objectId: String, e: BmobException?) {
                Log.d("jiaBing", "addUser错误--${e?.message}")
                if (e == null) {
                    addSuccessCallback.invoke(objectId)
                } else {
                    addFailCallback.invoke(e)
                }
            }
        })
    }

    /**
     * 查询用户是否存在
     */
    fun queryUserHave(
        userBean: UserBean,
        addSuccessCallback: (objectId: String) -> Unit,
        addFailCallback: (objectId: BmobException) -> Unit
    ) {
        val queryUser = BmobQuery<UserBean>()
        queryUser.findObjects(object : FindListener<UserBean>() {
            override fun done(data: MutableList<UserBean>?, e: BmobException?) {
                Log.d("jiaBing", "queryUserHave错误--${e?.message}")
                data?.let {
                    for (item in it) {
                        if (item.phone == userBean.phone) {
                            //手机号已存在
                            //用户已存在，直接就登录了，不添加数据了
                            addSuccessCallback("")
                            return
                        }
                    }
                    addUser(userBean, addSuccessCallback, addFailCallback)
                }
            }
        })
    }

    fun addFeedBack(
        message: String,
        addSuccessCallback: (objectId: String) -> Unit,
        addFailCallback: (objectId: BmobException) -> Unit
    ) {
        val feedBackBean = FeedBackBean(message)
        feedBackBean.save(object : SaveListener<String>() {
            override fun done(objectId: String, e: BmobException?) {

                if (e == null) {
                    addSuccessCallback.invoke(objectId)
                } else {
                    addFailCallback.invoke(e)
                }
            }

        })
    }
}