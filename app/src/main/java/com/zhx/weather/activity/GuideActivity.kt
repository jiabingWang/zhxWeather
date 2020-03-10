package com.zhx.weather.activity

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.zhx.weather.R
import com.zhx.weather.base.BaseActivity
import com.zhx.weather.util.setImageFromR
import kotlinx.android.synthetic.main.activity_guide.*
import org.jetbrains.anko.startActivity
import java.util.ArrayList

class GuideActivity : BaseActivity() {
    private var mImageViewList = ArrayList<ImageView>()// imageView集合
    // 引导页图片id数组
    private val mImageIds =
        intArrayOf(R.drawable.ic_guide1, R.drawable.ic_guide2, R.drawable.ic_guide3)
    // 小红点移动距离
    private var mPointDis: Int = 0

    override fun getLayoutResOrView(): Int = R.layout.activity_guide
    override fun initData() {

    }

    override fun initUi(savedInstanceState: Bundle?) {
        initPoint()
        initVp()
    }

    override fun initListener() {
        btn_start.setOnClickListener {
            startActivity<MainActivity>()
        }
    }

    private fun initPoint() {
        for (i in mImageIds.indices) {
            val view = ImageView(this)
            view setImageFromR mImageIds[i]
            //            view.setBackgroundResource(mImageIds[i]);// 通过设置背景,可以让宽高填充布局
            // view.setImageResource(resId)
            mImageViewList.add(view)

            // 初始化小圆点
            val point = ImageView(this)
            point.setImageResource(R.drawable.shape_point_gray)// 设置图片(shape形状)

            // 初始化布局参数, 宽高包裹内容,父控件是谁,就是谁声明的布局参数
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            if (i > 0) {
                // 从第二个点开始设置左边距
                params.leftMargin = 10
            }

            point.layoutParams = params// 设置布局参数

            ll_container.addView(point)// 给容器添加圆点
        }
    }

    private fun initVp() {
        vp_guide.adapter = GuideAdapter()// 设置数据
        vp_guide.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                // 当页面滑动过程中的回调
                println(
                    "当前位置:" + position + ";移动偏移百分比:"
                            + positionOffset
                )
                // 更新小红点距离
                val leftMargin =
                    ((mPointDis * positionOffset).toInt() + (position * mPointDis))// 计算小红点当前的左边距
                val params = iv_red_point
                    .getLayoutParams() as RelativeLayout.LayoutParams
                params.leftMargin = leftMargin// 修改左边距

                // 重新设置布局参数
                iv_red_point.setLayoutParams(params)
            }

            override fun onPageSelected(position: Int) {
                if (position == mImageViewList.size - 1) {// 最后一个页面显示开始体验的按钮
                    btn_start.setVisibility(View.VISIBLE)
                } else {
                    btn_start.setVisibility(View.INVISIBLE)
                }
            }

        })


        // 计算两个圆点的距离
        // 移动距离=第二个圆点left值 - 第一个圆点left值
        // measure->layout(确定位置)->draw(activity的onCreate方法执行结束之后才会走此流程)
        // mPointDis = llContainer.getChildAt(1).getLeft()
        // - llContainer.getChildAt(0).getLeft();
        // System.out.println("圆点距离:" + mPointDis);

        // 监听layout方法结束的事件,位置确定好之后再获取圆点间距
        // 视图树
        iv_red_point.viewTreeObserver.addOnGlobalLayoutListener(
            object : ViewTreeObserver.OnGlobalLayoutListener {

                override fun onGlobalLayout() {
                    // 移除监听,避免重复回调
                    iv_red_point.viewTreeObserver
                        .removeGlobalOnLayoutListener(this)
                    // ivRedPoint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    // layout方法执行结束的回调
                    mPointDis =
                        (ll_container.getChildAt(1).getLeft() - ll_container.getChildAt(0).getLeft())
                    println("圆点距离:$mPointDis")
                }
            })
    }
    internal inner class GuideAdapter : PagerAdapter() {
        override fun getCount(): Int {
            return mImageViewList.size

        }

        override fun isViewFromObject(arg0: View, arg1: Any): Boolean {
            return arg0 === arg1
        }

        // 初始化item布局
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = mImageViewList[position]
            container.addView(view)
            return view
        }

        // 销毁item
        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }
    }
}
