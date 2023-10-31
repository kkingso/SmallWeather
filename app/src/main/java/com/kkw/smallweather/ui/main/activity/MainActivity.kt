package com.kkw.smallweather.ui.main.activity

import android.view.View
import androidx.viewpager2.widget.ViewPager2
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
        val vp2Adapter = CityViewPager2Adapter(this)
        mBinding.weatherVp2.adapter = vp2Adapter
        // 设置指示器个数
        mBinding.vp2Indicator.setIndicatorItemCount(vp2Adapter.itemCount)
        // 监听vp2界面变化
        mBinding.weatherVp2.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                // 显示在哪个页面就重绘对应的指示器
                mBinding.vp2Indicator.setCurrentSelectedPosition(mBinding.weatherVp2.currentItem)
                mBinding.vp2Indicator.postInvalidate()
            }
        })
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