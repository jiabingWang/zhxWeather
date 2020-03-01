package com.zhx.weather.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.CountDownTimer
import android.util.AttributeSet

import androidx.appcompat.widget.AppCompatTextView
import com.zhx.weather.R

class TimingButton(context: Context, attrs: AttributeSet) : AppCompatTextView(context, attrs) {
    private val total: Int
    private val interval: Int
    private val psText: String?

    init {
        val typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.flower_TimingButton)
        total = typedArray.getInteger(R.styleable.flower_TimingButton_flower_tb_totalTime, 60000)
        interval = typedArray.getInteger(R.styleable.flower_TimingButton_flower_tb_intervalTime, 1000)
        psText = typedArray.getString(R.styleable.flower_TimingButton_flower_tb_finishText)
        typedArray.recycle()
    }

    fun start() {
        val time = TimeCount(total.toLong(), interval.toLong())
        time.start()
    }

    inner class TimeCount(millisInFuture: Long, private val countDownInterval: Long) :
        CountDownTimer(millisInFuture, countDownInterval) {

        override fun onFinish() {
            text = psText
            isEnabled = true
        }

        @SuppressLint("SetTextI18n")
        override fun onTick(millisUntilFinished: Long) {
            isEnabled = false
            text = ((millisUntilFinished + countDownInterval) / countDownInterval).toString() + "秒"
        }
    }

}
