package com.zhx.weather.base

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.zhx.weather.listener.MessageBusInterface
import com.zhx.weather.listener.NormalInterface

/*
 * 描述：FG基类
 */
abstract class BaseFragment :Fragment(), MessageBusInterface, NormalInterface {
    /**动态权限请求码*/
    private val needPermissionCode = 1024
    /**
     * 动态权限的回调，是否有此权限
     * 动态权限的回调，是否勾选了不再提示
     */
    private var needPermissionCall: ((granted: Boolean,checked: Boolean) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutResOrView(),container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi(savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
        //消息总线注册
        MessageBus.register(this)
        initListener()
    }

    override fun getPermission(
        permission: MutableList<String>,
        needPermissionCall: (granted: Boolean,checked :Boolean) -> Unit
    ) {
        if (context == null) {
            return
        }
        var isNeedRequest = false
        val list = mutableListOf<String>()
        permission.forEach {
            if (ActivityCompat.checkSelfPermission(
                    context!!,
                    it
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                isNeedRequest = true
                list.add(it)
            }
        }
        if (isNeedRequest) {
            this.needPermissionCall = needPermissionCall
            if (list.isNotEmpty()) {
                requestPermissions(list.toTypedArray(), needPermissionCode)
            } else {
                needPermissionCall.invoke(true,false)
            }
        } else {
            needPermissionCall.invoke(true,false)
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
            for (i in 0 until grantResults.size){
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    isGetAllPermission = false
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(activity!!,permissions[i])){
                        checked =true
                    }
                }
            }
            needPermissionCall?.invoke(isGetAllPermission,checked)
        }
    }
    override fun onDestroy() {
        MessageBus.unRegister(this)
        super.onDestroy()
    }
}