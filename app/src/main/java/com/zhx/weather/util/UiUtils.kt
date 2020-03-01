package com.zhx.weather.util

import android.app.Activity
import android.graphics.Color
import android.graphics.Rect
import android.os.Build
import android.text.*
import android.text.style.AbsoluteSizeSpan
import android.view.*
import android.widget.EditText
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.ColorUtils
import com.scwang.smartrefresh.layout.util.SmartUtil
import com.zhx.weather.base.BaseActivity
import com.zhx.weather.base.BaseFragment
import com.zhx.weather.listener.KeyboardVisibilityEventListener

/**
 * 描述：和UI相关的一些工具方法
 */

/**设置状态栏颜色，默认为白色*/
fun setStatusBarColor(activity: Activity,@ColorInt color: Int = Color.WHITE) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

        // 设置状态栏底色颜色
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        activity.window.statusBarColor = color

        // 判断颜色是不是亮色
        // 如果亮色，设置状态栏文字为黑色
        if (ColorUtils.calculateLuminance(color) >= 0.5) {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        }
    }
}
/////////////////////////////////////////////////////////////////////////////////////////
//** EditText设置字体大小和内容
/////////////////////////////////////////////////////////////////////////////////////////
fun EditText.setHintWithSize(hintText: String, size: Int) {
    if (!TextUtils.isEmpty(hintText)) {
        val ss = SpannableString(hintText)
        //设置字体大小 true表示单位是sp
        val ass = AbsoluteSizeSpan(size, true)
        ss.setSpan(ass, 0, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        this.hint = SpannedString(ss)
    }
}

/**
 * Set keyboard visibility change event listener.
 *
 * @param activity Activity
 * @param listener KeyboardVisibilityEventListener
 */
fun setVisibilityEventListener(
    activity: Activity?,
    listener: KeyboardVisibilityEventListener?
) {

    if (activity == null) {
        throw NullPointerException("Parameter:activity must not be null")
    }

    if (listener == null) {
        throw NullPointerException("Parameter:listener must not be null")
    }

    val activityRoot = getActivityRoot(activity)

    val layoutListener = object : ViewTreeObserver.OnGlobalLayoutListener {

        private val r = Rect()

        private val visibleThreshold = SmartUtil.dp2px(100f)

        private var wasOpened = false

        override fun onGlobalLayout() {
            activityRoot.getWindowVisibleDisplayFrame(r)

            val heightDiff = activityRoot.getRootView().getHeight() - r.height()

            val isOpen = heightDiff > visibleThreshold

            if (isOpen == wasOpened) {
                // keyboard state has not changed
                return
            }

            wasOpened = isOpen

            val removeListener = listener!!.onVisibilityChanged(isOpen, heightDiff)
            if (removeListener) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    activityRoot.viewTreeObserver
                        .removeOnGlobalLayoutListener(this)
                } else {
                    activityRoot.viewTreeObserver
                        .removeGlobalOnLayoutListener(this)
                }
            }
        }
    }
    activityRoot.viewTreeObserver.addOnGlobalLayoutListener(layoutListener)
}
/**
 * 获取activity的根view
 */
fun getActivityRoot(activity: Activity): View {
    return (activity.findViewById<View>(Window.ID_ANDROID_CONTENT) as ViewGroup).getChildAt(0)
}
fun EditText.textAble(text:String){
    this.text = Editable.Factory.getInstance().newEditable(text)
}
/////////////////////////////////////////////////////////////////////////////////////////
//** dialog
/////////////////////////////////////////////////////////////////////////////////////////
fun BaseActivity.showProgressDialog(tip:String) {
    com.kongzue.dialog.v3.WaitDialog.show(this, tip)
}
fun BaseActivity.showSuccessDialog(msg:String,tipTime:Int = 1000) {
    com.kongzue.dialog.v3.WaitDialog.show(this, msg, com.kongzue.dialog.v3.TipDialog.TYPE.SUCCESS).setTipTime(tipTime)
}
fun BaseActivity.showErrorDialog(msg:String,tipTime:Int = 1000) {
    com.kongzue.dialog.v3.WaitDialog.show(this, msg, com.kongzue.dialog.v3.TipDialog.TYPE.ERROR).setTipTime(tipTime)
}

fun BaseFragment.showProgressDialog(tip:String) {
    com.kongzue.dialog.v3.WaitDialog.show(this.activity as AppCompatActivity, tip)
}
fun BaseFragment.showSuccessDialog(msg:String,tipTime:Int = 1000) {
    com.kongzue.dialog.v3.WaitDialog.show(this.activity as AppCompatActivity, msg,
        com.kongzue.dialog.v3.TipDialog.TYPE.SUCCESS).setTipTime(tipTime)
}
fun BaseFragment.showErrorDialog(msg:String,tipTime:Int = 1000) {
    com.kongzue.dialog.v3.WaitDialog.show(this.activity as AppCompatActivity, msg,
        com.kongzue.dialog.v3.TipDialog.TYPE.ERROR).setTipTime(tipTime)
}
/////////////////////////////////////////////////////////////////////////////////////////
//** dialog
/////////////////////////////////////////////////////////////////////////////////////////