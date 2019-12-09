package com.zhx.weather.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zhx.weather.R
import com.zhx.weather.bean.LifestyBean
import com.zhx.weather.util.setCircleImageFromR
import com.zhx.weather.util.setImageFromR
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
        holder.ivlogo.setBackgroundColor(getIconColor(data[position].type))
        holder.ivlogo setCircleImageFromR getIcon(data[position].type)
        holder.tvType textFrom getType(data[position].type)
        holder.tvBrf textFrom data[position].brf
    }

    inner class LifestyleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivlogo = itemView.findViewById<ImageView>(R.id.iv_logo)
        val tvType = itemView.findViewById<TextView>(R.id.tv_type)
        val tvBrf = itemView.findViewById<TextView>(R.id.tv_brf)
    }

    /**
     * 根据接口返回的简写拼音，显示正确的类型
     * comf：舒适度指数、cw：洗车指数、drsg：穿衣指数、flu：感冒指数、sport：运动指数、trav：旅游指数、uv：紫外线指数、air：空气污染扩散条件指数
     */
    private fun getType(type: String): String {
        return when (type) {
            "comf" -> "舒适度"
            "cw" -> "洗车"
            "drsg" -> "穿衣"
            "flu" -> "感冒"
            "sport" -> "运动"
            "trav" -> "旅游"
            "uv" -> "紫外线"
            "air" -> "空气污染"
            else -> "未知"
        }
    }

    /**
     * 根据接口返回的简写拼音，显示正确的Icon资源
     */
    private fun getIcon(type: String): Int {
        return when (type) {
            "comf" -> R.drawable.ic_comf
            "cw" -> R.drawable.ic_cw
            "drsg" -> R.drawable.ic_drsg
            "flu" -> R.drawable.ic_flu
            "sport" -> R.drawable.ic_sport
            "trav" -> R.drawable.ic_trav
            "uv" -> R.drawable.ic_uv
            "air" -> R.drawable.ic_air
            else -> R.drawable.ic_air
        }
    }
    /**
     * 根据接口返回的简写拼音，显示正确的Icon资源,因为是矢量图，所以需要颜色
     */
    private fun getIconColor(type: String): Int {
        return when (type) {
            "comf" -> -0x1661c4
            "cw" -> -0x9d4e01
            "drsg" -> -0x703aa1
            "flu" -> -0x67e88
            "sport" -> -0x4c35a0
            "trav" -> -0x293cb
            "uv" -> -0xf54d6
            "air" -> -0x806117
            else -> -0x806117
        }
    }
}