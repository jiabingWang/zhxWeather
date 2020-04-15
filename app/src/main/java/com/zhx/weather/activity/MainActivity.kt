package com.zhx.weather.activity

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import com.google.android.material.snackbar.Snackbar
import com.zhx.weather.R
import com.zhx.weather.base.BaseActivity
import com.zhx.weather.base.BaseFragment
import com.zhx.weather.common.MSG_LOGIN_OUT
import com.zhx.weather.common.MSG_LOGIN_SUCCESS
import com.zhx.weather.fragment.WeatherFragment
import com.zhx.weather.manager.UserInfoManager
import com.zhx.weather.util.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
            userLoginChange(true)
        }

    }
    suspend fun ddd(){
        withContext(Dispatchers.Main){

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
                    startActivity<SearchWeatherActivity>()
                }
                R.id.nav_calendar -> {
                    toast("万年历")
                }

                R.id.nav_about -> {
                    if (UserInfoManager.INSTANCE.isLogin()){
                        toast("您已登录")
                    }else{
                        startActivity<LoginActivity>()
                    }
                }
                R.id.nav_clock -> {
                    toast("天气闹钟")
                }
                R.id.nav_feedback -> {
                    startActivity<FeedBackActivity>()
                }
                R.id.nav_about_us -> {
                    startActivity<AboutUsActivity>()
                }
                R.id.nav_set -> {
                    startActivity<SettingActivity>()
//                    setVoiceName()
                }
            }
            drawer_layout.closeDrawers()
            true
        }
    }

    override fun onMessageBus(code: Int, event: Any?) {
        super.onMessageBus(code, event)
        if (code == MSG_LOGIN_SUCCESS) {
            userLoginChange(true)
        }
        if (code == MSG_LOGIN_OUT){
            userLoginChange(false)
        }
    }

    /**
     * 用户登录的改变
     */
    private fun userLoginChange(isLogin:Boolean) {
        if (isLogin){
            val header = nav_menu.getHeaderView(0).findViewById<ImageView>(R.id.iv_header)
            val phone = nav_menu.getHeaderView(0).findViewById<TextView>(R.id.tv_phone)
            header setCircleImageFromNet "https://resources.ninghao.org/images/space-skull.jpg"
            phone textFrom UserInfoManager.INSTANCE.getUserId()
            header.isClickable = false
            phone.isClickable = false
        }else{
            val header = nav_menu.getHeaderView(0).findViewById<ImageView>(R.id.iv_header)
            val phone = nav_menu.getHeaderView(0).findViewById<TextView>(R.id.tv_phone)
            header setImageFromR R.drawable.icon_default_header
            phone textFrom "请登录"
            header.isClickable = true
            phone.isClickable = true
        }

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
                ActionBarDrawerToggle(this, drawer_layout, it,
                    R.string.open,
                    R.string.close
                ) {
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
