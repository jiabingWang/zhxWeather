package com.zhx.weather.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zhx.weather.R
import com.zhx.weather.util.textFrom

/**
 * 路漫漫其修远兮，吾将上下而求索
 * @author 服装学院的IT男
 * 时间: on 2020-02-27
 * 包名 com.zhx.weather.adapter
 * 描述：
 */
class AddressAdapter(private val context: Context, var list: List<String>,private val onDelectedItemClick:(String)->Unit) :
    BaseQuickAdapter<String,BaseViewHolder>(R.layout.item_address,list) {
    override fun convert(helper: BaseViewHolder?, item: String?) {
        val address =helper?.getView<TextView>(R.id.tv_address)!!
        val delecte =helper?.getView<TextView>(R.id.tv_delete)!!
        address textFrom item
        if(item ==list[0]){
            delecte.visibility = View.GONE
        }else{
            delecte.visibility = View.VISIBLE
        }
        address?.setOnClickListener {
//            onItemClick.invoke(item!!)
        }
        delecte.setOnClickListener{
            onDelectedItemClick.invoke(item!!)
        }
    }



}