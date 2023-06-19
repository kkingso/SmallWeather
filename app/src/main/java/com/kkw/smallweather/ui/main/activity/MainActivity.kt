package com.kkw.smallweather.ui.main.activity

import android.util.Log
import android.view.View
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
import com.kkw.smallweather.view.HourlyItemDecoration

/**
 * 天气主页
 */
class MainActivity : BaseActivity() {
    lateinit var mBinding: ActivityMainBinding
    lateinit var mDailyAdapter: DailyWeatherAdapter
    lateinit var mHourlyAdapter: HourlyWeatherAdapter
    lateinit var mHelper: QuickAdapterHelper

    override fun getLayoutId(): View {
        TAG = "MainActivity"
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun initView() {
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
}