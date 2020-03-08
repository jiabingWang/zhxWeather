package com.zhx.weather

import android.content.DialogInterface
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.zhx.weather.activity.LoginActivity
import com.zhx.weather.base.BaseActivity
import com.zhx.weather.base.BaseFragment
import com.zhx.weather.common.MSG_LOGIN_SUCCESS
import com.zhx.weather.fragment.WeatherFragment
import com.zhx.weather.manager.UserInfoManager
import com.zhx.weather.util.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.header.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * 主Activity
 */
class MainActivity : BaseActivity() {

    /**
     * 当前显示的Fg
     */
    private var mCurrentFg: BaseFragment? = null
    /**
     * 天气Fg
     */
    private var mWeatherFg: BaseFragment? = null

    private var mDrawerToggle: ActionBarDrawerToggle? = null
    override fun getLayoutResOrView() = R.layout.activity_main
    override fun initData() {

    }

    override fun initUi(savedInstanceState: Bundle?) {
        setFragment()
        if (UserInfoManager.INSTANCE.isLogin()) {
            userLoginChange()
        }
    }

    override fun initListener() {
        nav_menu.getHeaderView(0).findViewById<TextView>(R.id.tv_phone).setOnClickListener {
            //登录
            startActivity<LoginActivity>()
        }
        nav_menu.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_search -> {
                    toast("查询城市天气")
                }
                R.id.nav_calendar -> {
                    toast("万年历")
                }
                R.id.nav_switch -> {
                    toast("切换城市")
                }
                R.id.nav_news -> {
                    toast("厕所刷刷刷")
                }
                R.id.nav_set -> {
                    setVoiceName()
                }
                R.id.nav_about -> {
                    toast("登录")
                }
                R.id.nav_clock -> {
                    toast("天气闹钟")
                }
            }
            drawer_layout.closeDrawers()
            true
        }
    }

    override fun onMessageBus(code: Int, event: Any?) {
        super.onMessageBus(code, event)
        if (code == MSG_LOGIN_SUCCESS) {
            userLoginChange()
        }
    }

    /**
     * 用户登录的改变
     */
    private fun userLoginChange() {
        val header = nav_menu.getHeaderView(0).findViewById<ImageView>(R.id.iv_header)
        val phone = nav_menu.getHeaderView(0).findViewById<TextView>(R.id.tv_phone)
        header setCircleImageFromNet "https://resources.ninghao.org/images/space-skull.jpg"
        phone textFrom UserInfoManager.INSTANCE.getUserId()
        header.isClickable = false
        phone.isClickable = false
    }

    private fun setFragment() {
        supportFragmentManager.beginTransaction().apply {
            if (mWeatherFg == null) {
                mWeatherFg = WeatherFragment.newInstance()
                add(R.id.fg_weather, mWeatherFg!!)
            }
            mCurrentFg = mWeatherFg
            mCurrentFg?.let {
                show(it)
            }
        }.commit()
    }

    fun initDrawer(toolbar: Toolbar?) {
        toolbar?.let {
            mDrawerToggle = object :
                ActionBarDrawerToggle(this, drawer_layout, it, R.string.open, R.string.close) {
                override fun onDrawerOpened(drawerView: View) {
                    super.onDrawerOpened(drawerView)
                }

                override fun onDrawerClosed(drawerView: View) {
                    super.onDrawerClosed(drawerView)
                }
            }
            mDrawerToggle?.syncState()
            drawer_layout.addDrawerListener(mDrawerToggle!!)
        }

    }

    private fun showDrawerLayout() {
        if (!drawer_layout.isDrawerOpen(Gravity.LEFT)) {
            drawer_layout.openDrawer(Gravity.LEFT)
        } else {
            drawer_layout.closeDrawer(Gravity.LEFT)
        }
    }


    private fun setVoiceName(){
        var voiceName = "xiaoyu"
        val builder = AlertDialog.Builder(this)
        builder.setIcon(android.R.drawable.ic_dialog_info)
        builder.setTitle("请选择发音人")
        val items = arrayOf(
            "小燕(女普通话)",
            "小宇(女普通话)",
            "小梅(粤语)",
            "小蓉(四川话)",
            "小倩(东北话)",
            "小坤(河南话)",
            "小强(湖南话)",
            "小莹(陕西话)",
            "小新(童年男声)",
            "楠楠(童年女声)",
            "老孙(老年男声)"
        )
        builder.setSingleChoiceItems(items, -1
        ) { dialog, which ->
            //which指的是用户选择的条目的下标
            //dialog:触发这个方法的对话框
            //                Toast.makeText(WeatherActivity.this, "您选择的是:"+items[which], Toast.LENGTH_SHORT).show();
            when (which) {
                0 -> voiceName = "xiaoyan"
                1 -> voiceName = "xiaoyu"
                2 -> voiceName = "xiaomei"
                3 -> voiceName = "xiaorong"
                4 -> voiceName = "xiaoqian"
                5 -> voiceName = "xiaokun"
                6 -> voiceName = "xiaoqiang"
                7 -> voiceName = "vixying"
                8 -> voiceName = "xiaoxin"
                9 -> voiceName = "nannan"
                10 -> voiceName = "vils"
                else -> {
                }
            }
            UserInfoManager.INSTANCE.setVoiceName(voiceName)
            dialog.dismiss()
            TTSManager.destroy()//重新选择声源后需要停止当前播报，并且为下一次能重新设置生源准备。这点很重要！！
        }
        builder.show()
    }
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            if (!DoubleClickExit.check()) {
                Snackbar.make(
                    this@MainActivity.window.decorView.findViewById(android.R.id.content),
                    "再按一次退出 App!",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                super.onBackPressed()
                System.exit(0)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        TTSManager.destroy()
    }
}
