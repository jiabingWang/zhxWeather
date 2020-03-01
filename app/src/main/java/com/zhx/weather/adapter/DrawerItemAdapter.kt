package com.zhx.weather.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
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
class DrawerItemAdapter(private val context: Context, var data :MutableList<DrawerBean>):RecyclerView.Adapter<DrawerItemAdapter.DrawerItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrawerItemViewHolder {
        return DrawerItemViewHolder(View.inflate(context,R.layout.item_drawer,null))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: DrawerItemViewHolder, position: Int) {
        holder.icon setImageFromR  data[position].icon
        holder.title textFrom data[position].title
    }

    inner class DrawerItemViewHolder(itemView :View) :RecyclerView.ViewHolder(itemView){
        val icon = itemView.findViewById<ImageView>(R.id.iv_icon)
        val title = itemView.findViewById<TextView>(R.id.tv_title)
    }


}