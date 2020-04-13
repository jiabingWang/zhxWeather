package com.zhx.weather.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import com.zhx.weather.R
import com.zhx.weather.base.BaseActivity
import com.zhx.weather.base.MessageBus
import com.zhx.weather.bean.UserBean
import com.zhx.weather.common.MSG_LOGIN_SUCCESS
import com.zhx.weather.listener.KeyboardVisibilityEventListener
import com.zhx.weather.manager.BmobDataManager
import com.zhx.weather.manager.UserInfoManager
import com.zhx.weather.util.*
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast

/**
 * 登录
 */
class LoginActivity : BaseActivity() {
    override fun getLayoutResOrView(): Int =R.layout.activity_login

    override fun initData() {
    }

    override fun initUi(savedInstanceState: Bundle?) {
        setEditText()
    }

    override fun initListener() {
        setKeyboardListener()
        btn_auth?.setOnClickListener {
            if (!checkPhone()) {
                toast("手机号格式不正确")
                return@setOnClickListener
            }
            getAuthCode()
        }

        tv_login?.setOnClickListener {
            //            WaitDialog.show(mContext!!, "正在登录...")
            val phone = et_phone?.text.toString().trim()
            val authCode = et_sms?.text.toString().trim()
            doLogin(phone, authCode)
        }

        et_phone?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                enableLogin()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        et_sms?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                enableLogin()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        cb_agreement.setOnCheckedChangeListener { _, _ ->
            enableLogin()
        }
        tv_user_agreement?.setOnClickListener {
            toast("跳转用户协议")
        }
        tv_privacy_agreement?.setOnClickListener {
            toast("跳转隐私协议")
        }
    }

    /**
     * 获取验证码
     */
    private fun getAuthCode() {
        et_sms.textAble("1234")
    }
    private fun doLogin(phone: String, authCode: String) {
            if (authCode.isEmpty()){
                toast("请填写验证码")
            }else{
                UserInfoManager.INSTANCE.setUserId(phone)
                val user= UserBean(phone,"测试")
                BmobDataManager.INSTANCE.queryUserHave(user,{
                    toast("登录成功")
                    MessageBus.post(MSG_LOGIN_SUCCESS,null)
                    finish()
                }){
                    toast("登录失败")
                }

            }
    }
    /**
     * 设置EditText的字体大小
     */
    private fun setEditText() {
        et_phone?.setHintWithSize("请输入您的手机号", 15)
        et_sms?.setHintWithSize("请输入验证码", 15)
    }
    private fun setKeyboardListener() {
        setVisibilityEventListener(
            this@LoginActivity,
            KeyboardVisibilityEventListener { isOpen, _ ->

                false
            }
        )
    }
    private fun checkPhone(): Boolean {
        val phoneValue = et_phone?.text.toString().trim()
        return if (TextUtils.isEmpty(phoneValue)) {
            false
        } else phoneValue.length == 11 && phoneValue.startsWith("1")
    }

    private fun checkAuthCode(): Boolean {
        val authCode = et_sms?.text.toString().trim()
        return authCode.length == 4
    }

    /**
     * 设置是否可登录
     */
    private fun enableLogin(): Boolean {
        val isLogin = checkPhone() && checkAuthCode() && cb_agreement?.isChecked!!
        tv_login?.isEnabled = isLogin
        return isLogin
    }

}
