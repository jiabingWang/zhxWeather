package com.zhx.weather

import android.app.Application
import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import cn.bmob.v3.Bmob
import com.scwang.smartrefresh.header.BezierCircleHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.umeng.commonsdk.UMConfigure
import com.zhx.weather.net.initOkHttp

/**
 * 包名 com.wjb.momweather
 * 描述：
 */
class WeatherApp :Application() {
    val umengKey = "5e6ca9dc167edd2de2000151"
    val umengMessageSecret = "29da373e80066bf3bff56b06f9552802"
    companion object{
        lateinit var app: Application
    }
    override fun onCreate() {
        super.onCreate()
        app = this
        initOkHttp()
//        DoraemonKit.install(this)
        //全局下拉格式
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, _ ->
            BezierCircleHeader(context)
        }
        //第初始化bmob
        Bmob.initialize(this, "com.zhx.weather")
        //um
        UMConfigure.init(
            app,
            "",
            "android",
            UMConfigure.DEVICE_TYPE_PHONE,
            umengMessageSecret
        )
//        val mPushAgent = PushAgent.getInstance(context)
//        mPushAgent.resourcePackageName = BuildConfig.APPLICATION_ID
//        //注册推送服务，每次调用register方法都会回调该接口
//        mPushAgent.register(object : IUmengRegisterCallback {
//            override fun onSuccess(deviceToken: String) {
//                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
//                "友盟设备token为$deviceToken".d()
//            }
//
//            override fun onFailure(s: String, s1: String) {
//                "友盟设备token获取失败原因$s--$s1".d()
//            }
//        })
//        setAlias()
//        //小米推送
//        MiPushRegistar.register(this, BuildConfig.umengXiaoMiId, BuildConfig.umengXiaoMiKey)
//        //华为推送
//        HuaWeiRegister.register(this)
//        //魅族推送
//        MeizuRegister.register(this, BuildConfig.umengMeiZuAppId, BuildConfig.umengMeiZuAppKey)
//        //OPPO推送
//        OppoRegister.register(this, BuildConfig.umengOPPOAppKey, BuildConfig.umengOPPOAppSecret)
//        //VIVO推送
//        VivoRegister.register(this)

//        val messageHandler = object : UmengMessageHandler() {
//            override fun getNotification(context: Context, msg: UMessage): Notification {
//                "友盟收到信息-builder_id--${msg.builder_id}".d()
//                MessageBus.post(MSG_UMENG_NOTIFICATION, null)
//                return NotifyObj.sendNotifyActivity(
//                    context,
//                    Intent(context, MoneyTabActivity::class.java),
//                    msg.title,
//                    msg.text,
//                    "subscribe",
//                    "订阅消息",
//                    R.mipmap.ic_launcher,
//                    R.mipmap.ic_launcher,
//                    NotificationManager.IMPORTANCE_DEFAULT
//                )
//            }
//        }
//        mPushAgent.messageHandler = messageHandler
    }
//    fun setAlias() {
//        val pushAgent = PushAgent.getInstance(app)
//        val uid = UserInfoManager.INSTANCE.getUserId()
//        uid?.let {
//            //绑定别名
//            ("友盟我的Uid为$it").d()
//            //alias_type="uid"
//            pushAgent.setAlias(it, "uid") { isSuccess, message ->
//                if (isSuccess) {
//                    "友盟绑定别名成功--$it".d()
//                } else {
//                    "友盟绑定别名失败-原因-$message".d()
//                }
//            }
//        }
//    }
}