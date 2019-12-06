package com.zhx.weather.adapter

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zhx.weather.R
import com.zhx.weather.bean.ForecastWeatherBean
import com.zhx.weather.util.setImageFromNet
import com.zhx.weather.util.textFrom

/**
 * 描述：未来3天的天气预报Adapter
 */
class ForecastAdapter(
    private val context: Context,
     var data: MutableList<ForecastWeatherBean.HeWeather6Bean.DailyForecastBean>
) : RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        return ForecastViewHolder(View.inflate(context, R.layout.item_forecast_layout, null))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {

        holder.ivCode setImageFromNet "https://cdn.heweather.com/cond_icon/${data[position].cond_code_d}.png"
        holder.tvTmpMin textFrom "${data[position].tmp_min}°"
        holder.tvTmpMax textFrom "${data[position].tmp_max}°"
        holder.tvData textFrom data[position].date
    }

    inner class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivCode = itemView.findViewById<ImageView>(R.id.iv_weather)
        val tvTmpMin = itemView.findViewById<TextView>(R.id.tv_tmp_min)
        val tvTmpMax = itemView.findViewById<TextView>(R.id.tv_tmp_max)
        val tvData = itemView.findViewById<TextView>(R.id.tv_data)
    }
}