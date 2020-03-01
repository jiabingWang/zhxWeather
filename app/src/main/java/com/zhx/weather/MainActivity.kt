package com.zhx.weather

import android.Manifest
import android.os.Bundle
import android.os.UserManager
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.zhx.weather.activity.LoginActivity
import com.zhx.weather.adapter.DrawerItemAdapter
import com.zhx.weather.base.BaseActivity
import com.zhx.weather.base.BaseFragment
import com.zhx.weather.base.MessageBus
import com.zhx.weather.bean.DrawerBean
import com.zhx.weather.common.MSG_LOCATION
import com.zhx.weather.common.MSG_LOGIN_SUCCESS
import com.zhx.weather.fragment.WeatherFragment
import com.zhx.weather.manager.UserInfoManager
import com.zhx.weather.util.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

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
    private var mDrawerItemAdapter: DrawerItemAdapter? = null
    private var mDrawerItemData = mutableListOf<DrawerBean>()
    override fun getLayoutResOrView() = R.layout.activity_main
    override fun initData() {

    }

    override fun initUi(savedInstanceState: Bundle?) {
        setFragment()
        initDrawerRv()
        if(UserInfoManager.INSTANCE.isLogin()){
            userLoginChange()
        }
    }

    override fun initListener() {
        tv_phone.setOnClickListener {
            //登录
            startActivity<LoginActivity>()
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
        iv_header setImageFromR R.drawable.icon_login_header
        tv_phone textFrom UserInfoManager.INSTANCE.getUserId()
        iv_header.isClickable = false
        tv_phone.isClickable = false
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

    private fun initDrawerRv() {
        iv_bg setImageFromNet "https://resources.ninghao.org/images/keyclack.jpg"

        initDrawerRvData()
        mDrawerItemAdapter = DrawerItemAdapter(this, mDrawerItemData)
        rv_drawer_item.adapter = mDrawerItemAdapter
        rv_drawer_item.layoutManager = LinearLayoutManager(this)
    }

    private fun initDrawerRvData() {
        val search = DrawerBean(R.drawable.ic_search, "查找")
        val setting = DrawerBean(R.drawable.ic_settings, "设置")
        mDrawerItemData.add(search)
        mDrawerItemData.add(setting)
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
}
