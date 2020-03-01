package com.zhx.weather.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
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
        Log.d("jiaBingD", msg)
    }
}
fun Any.logE(msg: String?) {
    if (IS_DEBUG) {
        Log.e("jiaBingE", msg)
    }
}
fun Any.logI(msg: String?) {
    if (IS_DEBUG) {
        Log.i("jiaBingI", msg)
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
fun Context.myToast(s: String?) {
    s?.let {
        val view = Toast.makeText(this, "", Toast.LENGTH_SHORT).view
        val mToast = Toast(this)
        mToast.view = view
        mToast.setText(it)
        mToast.duration = Toast.LENGTH_SHORT
        mToast.show()
    }
}

fun Fragment.myToast(s: String?) {
    s?.let {
        val view = Toast.makeText(this.context, "", Toast.LENGTH_SHORT).view
        val mToast = Toast(this.activity)
        mToast.view = view
        mToast.setText(it)
        mToast.duration = Toast.LENGTH_SHORT
        mToast.show()
    }
}

fun Context.myShortToast(s: String?) {
    s?.let {
        val view = Toast.makeText(this, "", Toast.LENGTH_SHORT).view
        val mToast = Toast(this)
        mToast.view = view
        mToast.setText(it)
        mToast.duration = Toast.LENGTH_SHORT
        mToast.show()
    }
}
/////////////////////////////////////////////////////////////////////////////////////////
//** SharePreference持久化相关
/////////////////////////////////////////////////////////////////////////////////////////

/**
 * 描述：SharedPreferences 工具集合
 */
var SP_FILE_NAME = "cloud_config"

fun Context.getSp(): SharedPreferences {
    return getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE)
}

/**
 * 数据保存再sp，只支持sp支持的基本类型
 */
fun Context.spSet(keyName: String, keyValue: Any?) {
    if (keyValue == null) {
        return
    }
    try {
        getSp().edit().apply() {
            when (keyValue) {
                is String -> {
                    putString(keyName, keyValue)
                }
                is Boolean -> {
                    putBoolean(keyName, keyValue)
                }
                is Int -> {
                    putInt(keyName, keyValue)
                }
                is Float -> {
                    putFloat(keyName, keyValue)
                }
                is MutableSet<*> -> {
                    putStringSet(keyName, keyValue as MutableSet<String>)
                }
                is Long -> {
                    putLong(keyName, keyValue)
                }
                else -> {
                    putString(keyName, "")
                }
            }
        }.commit()
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}

fun Context.spRemove(keyName: String) {
    getSp().edit().remove(keyName).commit()
}

/**
 * 从sp获取数据
 */
fun <T> Context.spGet(keyName: String, default: T): T {
    try {
        var result: Any? = default
        when (default) {
            is String -> {
                result = getSp().getString(keyName, default)
            }
            is Boolean -> {
                result = getSp().getBoolean(keyName, default)
            }
            is Float -> {
                result = getSp().getFloat(keyName, default)
            }
            is Int -> {
                result = getSp().getInt(keyName, default)
            }
            is Long -> {
                result = getSp().getLong(keyName, default)
            }
            is Set<*> -> {
                result = getSp().getStringSet(keyName, default as MutableSet<String>)
            }
        }
        return result as T
    } catch (e: Throwable) {
        return default
    }
}


/////////////////////////////////////////////////////////////////////////////////////////
//** SharePreference持久化相关
/////////////////////////////////////////////////////////////////////////////////////////
