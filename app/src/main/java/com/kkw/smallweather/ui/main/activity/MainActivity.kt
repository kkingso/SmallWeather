package com.kkw.smallweather.ui.main.activity

import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.QuickAdapterHelper
import com.kkw.smallweather.BaseActivity
import com.kkw.smallweather.bean.BaseWeather
import com.kkw.smallweather.bean.DailyBean
import com.kkw.smallweather.bean.HourlyBean
import com.kkw.smallweather.bean.NowBean
import com.kkw.smallweather.callback.RequestCallback
import com.kkw.smallweather.databinding.ActivityMainBinding
import com.kkw.smallweather.http.HttpRepository
import com.kkw.smallweather.http.HttpRepository.await
import com.kkw.smallweather.ui.main.adapter.DailyWeatherAdapter
import com.kkw.smallweather.ui.main.adapter.HourlyWeatherAdapter

/**
 * 天气主页
 */
class MainActivity : BaseActivity() {
    lateinit var mBinding: ActivityMainBinding
    lateinit var mDailyAdapter: DailyWeatherAdapter
    lateinit var mHourlyAdapter: HourlyWeatherAdapter
    lateinit var mHelper: QuickAdapterHelper
    private var mNowCityName = "上海市"

    override fun getLayoutId(): View {
        TAG = "MainActivity"
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun initView() {
        showTitleCity(false)
        // 小时adapter
        mHourlyAdapter = HourlyWeatherAdapter()
        mBinding.hourlyRecyclerview.run {
            adapter = mHourlyAdapter
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.HORIZONTAL, false)
        }

        // 每天adapter
        mDailyAdapter = DailyWeatherAdapter()
        mBinding.dailyRecyclerview.run {
            adapter = mDailyAdapter
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        }

        mBinding.mainScrollview.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            val nowWeatherViewHeight = mBinding.weatherNowView.height
            val nowCityHeight = mBinding.nowCity.height
            Log.d(
                "kkkw",
                "scrollY={${scrollY}/${nowWeatherViewHeight}}, scale={${nowWeatherViewHeight / 3}}"
            )
            showTitleCity(scrollY >= nowCityHeight * 2 / 3)
            if (scrollY >= nowWeatherViewHeight / 3) {
                mBinding.weatherNowView.visibility = View.INVISIBLE
            } else {
                mBinding.weatherNowView.visibility = View.VISIBLE
            }

        }
    }

    override fun initData() {
        getNowWeather()
        getHourlyWeather()
        getDailyWeather()
    }

    private fun getNowWeather() {
        HttpRepository.weatherService.getWeatherNow("101021200")
            .await(object : RequestCallback<NowBean> {

                override fun onSuccess(data: BaseWeather<NowBean>?) {
                    data?.bean?.let { now ->
                        mBinding.nowTemp.text = now.temp
                        mBinding.nowText.text = now.text
                    }
                    data?.updateTime?.let {
                        mBinding.weatherUpdatetime.text = "上次更新时间：${it.substring(11..15)}"
                    }
                }

                override fun onFailure(t: Throwable) {
                }
            })
    }

    private fun getHourlyWeather() {
        HttpRepository.weatherService.getWeatherHourly(24, "101021200")
            .await(object : RequestCallback<MutableList<HourlyBean>> {

                override fun onSuccess(data: BaseWeather<MutableList<HourlyBean>>?) {
                    data?.bean?.let { hourlyList ->
                        Log.d(TAG, "size: ${hourlyList.size}")
                        mHourlyAdapter.submitList(hourlyList)
                    }
                }

                override fun onFailure(t: Throwable) {
                }
            })
    }

    private fun getDailyWeather() {
        HttpRepository.weatherService.getWeatherDaily(7, "101021200")
            .await(object : RequestCallback<MutableList<DailyBean>> {

                override fun onSuccess(data: BaseWeather<MutableList<DailyBean>>?) {
                    data?.bean?.let { dailyList ->
                        Log.d(TAG, "size: ${dailyList.size}")
                        mBinding.nowMaxMinTemp.maxTemp.text = "${dailyList[0].tempMax}℃"
                        mBinding.nowMaxMinTemp.minTemp.text = "${dailyList[0].tempMin}℃"
                        mDailyAdapter.submitList(dailyList)
                    }
                }

                override fun onFailure(t: Throwable) {
                }
            })
    }

    // 默认透明度
    private var statusAlpha = 0

    private fun setTopBackground() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mBinding.weatherNowView.setBackgroundColor(Color.argb(statusAlpha, 237, 112, 0))
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.argb(statusAlpha, 237, 112, 0)
        }
    }

    private fun showTitleCity(showTitle: Boolean) {
        mBinding.toolbarTitle.text = mNowCityName
        mBinding.nowCity.text = mNowCityName
        if (showTitle) {
            mBinding.toolbarTitle.visibility = View.VISIBLE
            mBinding.nowCity.visibility = View.INVISIBLE
        } else {
            mBinding.toolbarTitle.visibility = View.INVISIBLE
            mBinding.nowCity.visibility = View.VISIBLE
        }
    }

}