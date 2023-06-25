package com.kkw.smallweather.ui.main.activity

import android.util.Log
import android.view.View
import com.kkw.smallweather.BaseActivity
import com.kkw.smallweather.databinding.ActivityMainBinding
import com.kkw.smallweather.ui.main.adapter.CityViewPager2Adapter
import com.kkw.smallweather.ui.main.fragment.WeatherFragment

/**
 * 天气主页
 */
class MainActivity : BaseActivity(), WeatherFragment.OnTitleListener {
    lateinit var mBinding: ActivityMainBinding
    private var mNowCityName = "上海市"

    override fun getLayoutId(): View {
        TAG = "MainActivity"
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun initView() {
        mBinding.weatherVp2.adapter = CityViewPager2Adapter(this)
    }

    override fun initData() {
    }

    override fun changeTitleName(name: String?, showTitle: Boolean) {
        mBinding.toolbarTitle.text = name
        if (showTitle) {
            mBinding.toolbarTitle.visibility = View.VISIBLE
        } else {
            mBinding.toolbarTitle.visibility = View.INVISIBLE
        }
    }
}