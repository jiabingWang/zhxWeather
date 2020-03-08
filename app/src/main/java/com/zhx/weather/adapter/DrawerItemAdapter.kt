package com.zhx.weather.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zhx.weather.R
import com.zhx.weather.bean.DrawerBean
import com.zhx.weather.util.setImageFromR
import com.zhx.weather.util.textFrom

/**
 * 路漫漫其修远兮，吾将上下而求索
 * 时间: on 2020-01-30
 * 包名 com.zhx.weather.adapter
 * 描述：
 */
class DrawerItemAdapter(private val context: Context, var list :MutableList<DrawerBean>):BaseQuickAdapter
<DrawerBean,BaseViewHolder>(R.layout.item_drawer,list) {
    override fun convert(helper: BaseViewHolder?, item: DrawerBean) {
        val icon = helper!!.getView<ImageView>(R.id.iv_icon)
        val title = helper!!.getView<TextView>(R.id.tv_title)
        icon setImageFromR item.icon
        title textFrom item.title
    }



}