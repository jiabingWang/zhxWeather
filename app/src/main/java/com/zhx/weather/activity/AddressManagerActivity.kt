package com.zhx.weather.activity

import android.os.Bundle
import android.util.Log
import androidx.core.util.rangeTo
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.smarttop.library.bean.City
import com.smarttop.library.bean.County
import com.smarttop.library.bean.Province
import com.smarttop.library.bean.Street
import com.smarttop.library.widget.AddressSelector
import com.smarttop.library.widget.BottomDialog
import com.smarttop.library.widget.OnAddressSelectedListener
import com.zhx.weather.MainActivity
import com.zhx.weather.R
import com.zhx.weather.adapter.AddressAdapter
import com.zhx.weather.base.BaseActivity
import com.zhx.weather.base.MessageBus
import com.zhx.weather.bean.AddressBean
import com.zhx.weather.common.MSG_REFRESH_ADDRESS
import com.zhx.weather.manager.UserInfoManager
import com.zhx.weather.util.logD
import com.zhx.weather.util.spGet
import com.zhx.weather.util.spSet
import kotlinx.android.synthetic.main.activity_adress_manager.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.lang.StringBuilder

class AddressManagerActivity : BaseActivity(), OnAddressSelectedListener,
    AddressSelector.OnDialogCloseListener {
    private val mAddressData = mutableListOf<String>()
    private var mAddressDialog: BottomDialog? = null
    private val mAdapter = AddressAdapter(this@AddressManagerActivity, mAddressData) {
        mAddressData.remove(it)
        UserInfoManager.INSTANCE.setAddressList(mAddressData.toMutableSet())
        refreshUi()
    }

    /**
     *
     */
    override fun getLayoutResOrView(): Int = R.layout.activity_adress_manager

    override fun initData() {
        val data  =UserInfoManager.INSTANCE.getAddressList()
        data?.let {
            for (item in it){
                mAddressData.add(item)
            }

            refreshUi()
        }

    }

    override fun initUi(savedInstanceState: Bundle?) {
        initAddressDialog()
        initRV()
    }

    override fun initListener() {
        titleBar.leftView.setOnClickListener {
            startActivity<MainActivity>()
            finish()
        }
        titleBar.rightView.setOnClickListener {
            mAddressDialog?.show()
        }
    }

    private fun initRV() {
        rv_address.layoutManager = LinearLayoutManager(this@AddressManagerActivity)
        rv_address.adapter = mAdapter
    }

    private fun refreshAdapter(city: String) {
        if (mAddressData.contains(city)) {
            toast("请勿重复添加")
        } else {
            mAddressData.add(city)
            UserInfoManager.INSTANCE.setAddressList(mAddressData.toMutableSet())
            refreshUi()
            MessageBus.post(MSG_REFRESH_ADDRESS,null)
        }
    }

    private fun refreshUi() {
        mAdapter.setNewData(mAddressData)
        mAdapter.notifyDataSetChanged()
    }

    private fun initAddressDialog() {
        mAddressDialog = BottomDialog(this)
        mAddressDialog?.let {
            it.setOnAddressSelectedListener(this)
            it.setDialogDismisListener(this)
            it.setTextSize(14f)//设置字体的大小
            it.setIndicatorBackgroundColor(android.R.color.holo_orange_light)//设置指示器的颜色
            it.setTextSelectedColor(android.R.color.holo_orange_light)//设置字体获得焦点的颜色
            it.setTextUnSelectedColor(android.R.color.holo_blue_light)//设置字体没有获得焦点的颜色
        }
    }

    override fun onAddressSelected(
        province: Province?,
        city: City?,
        county: County?,
        street: Street?
    ) {
        Log.d("jiaBing","province${province?.name}--city${city?.name}--county${county?.name}")
        var address = ""
        if(province?.name =="香港"||province?.name =="台湾"||province?.name =="澳门"||province?.name =="钓鱼岛"){
            toast("该地区暂不支持")
            mAddressDialog?.dismiss()
            return
        }
        if (province?.name =="北京"||province?.name =="上海"||province?.name =="天津"){
            address = city!!.name
        }else{
            address = county!!.name
        }
        refreshAdapter(address)
        mAddressDialog?.dismiss()
    }

    override fun dialogclose() {
        mAddressDialog?.dismiss()
    }

    override fun onBackPressed() {
        startActivity<MainActivity>()
        finish()
    }
}
