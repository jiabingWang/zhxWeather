package com.zhx.weather.util

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.zhx.weather.R
import com.zhx.weather.common.IS_DEBUG
import org.jetbrains.anko.dip

/**
 * 描述：一些常用的 kotlin拓展方法
 */
/** Log */
fun Any.logD(msg: String?) {
    if (IS_DEBUG) {
        Log.d("jiaBingD", "msg")
    }
}
fun Any.logE(msg: String?) {
    if (IS_DEBUG) {
        Log.e("jiaBingE", "msg")
    }
}
fun Any.logI(msg: String?) {
    if (IS_DEBUG) {
        Log.i("jiaBingI", "msg")
    }
}
/** 拓展方法 */
/**
 * 设置textView从资源文件
 */
infix fun TextView.textFrom(@StringRes strRes: Int) {
    text = try {
        this.context.getText(strRes)
    } catch (e: Throwable) {
        strRes.toString()
    }
}

/**
 * 设置textView从资源文件
 */
infix fun TextView.textFrom(str: CharSequence?) {
    text = str
}
/**
 * 从一个url加载图片到view
 */
infix fun ImageView.setImageFromNet(url: String?) {
    if (url == null) {
        setImageResource(R.drawable.place_holder)
    } else {
        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.place_holder)
            .error(R.drawable.place_holder)
            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
            .into(this)
    }
}
/**
 * 从一个url加载圆形图片到view
 */
infix fun ImageView.setCircleImageFromNet(url: String?) {
    if (url == null) {
        setImageResource(R.drawable.place_holder)
    } else {
        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.place_holder)
            .error(R.drawable.place_holder)
            .apply(
                RequestOptions
                    .bitmapTransform(CircleCrop())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
            )
            .into(this)

    }
}
/**
 * 从一个url加载圆形图片到view，自定义圆角半径
 */
fun ImageView.setCircleImageFromNet(url: String?, radius: Int) {
    if (url == null) {
        setImageResource(R.drawable.place_holder)
    } else {
        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.place_holder)
            .error(R.drawable.place_holder)
            .apply(
                RequestOptions
                    .bitmapTransform(RoundedCorners(this.context.dip(radius)))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
            )
            .into(this)
    }
}
/**
 * 从资源文件图片到view
 */
infix fun ImageView.setImageFromR(resId: Int?) {
    if (resId == null) {
        setImageResource(R.drawable.place_holder)
    } else {
        Glide.with(this)
            .load(resId)
            .placeholder(R.drawable.place_holder)
            .error(R.drawable.place_holder)
            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
            .into(this)

    }
}
/**
 * 从资源文件加载图片到View（圆角）
 */
infix fun ImageView.setCircleImageFromR(resId: Int?) {
    if (resId == null) {
        setImageResource(R.drawable.place_holder)
    } else {
        Glide.with(this)
            .load(resId)
            .placeholder(R.drawable.place_holder)
            .error(R.drawable.place_holder)
            .apply(
                RequestOptions
                    .bitmapTransform(CircleCrop())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
            )
            .into(this)

    }
}
/**
 * 从资源文件加载图片到View（圆角）
 */
 fun ImageView.setCircleImageFromR(resId: Int?, radius: Int) {
    if (resId == null) {
        setImageResource(R.drawable.place_holder)
    } else {
        Glide.with(this)
            .load(resId)
            .placeholder(R.drawable.place_holder)
            .error(R.drawable.place_holder)
            .apply(
                RequestOptions
                    .bitmapTransform(RoundedCorners(this.context.dip(radius)))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
            )
            .into(this)

    }
}

