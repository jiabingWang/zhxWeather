package com.zhx.weather.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zhx.weather.R
import com.zhx.weather.bean.LifestyBean
import com.zhx.weather.util.textFrom

/**
 * 描述：生活指数Adapter
 */
class LifestyleAdapter(
    private val context: Context,
     var data: MutableList<LifestyBean.HeWeather6Bean.LifestyleBean>
) : RecyclerView.Adapter<LifestyleAdapter.LifestyleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LifestyleViewHolder {
        return LifestyleViewHolder(View.inflate(context, R.layout.item_lifesty_layout, null))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: LifestyleViewHolder, position: Int) {
        holder.tvType textFrom data[position].type
        holder.tvBrf textFrom  data[position].brf
    }

    inner class LifestyleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivlogo = itemView.findViewById<ImageView>(R.id.iv_logo)
        val tvType = itemView.findViewById<TextView>(R.id.tv_type)
        val tvBrf = itemView.findViewById<TextView>(R.id.tv_brf)
    }
}