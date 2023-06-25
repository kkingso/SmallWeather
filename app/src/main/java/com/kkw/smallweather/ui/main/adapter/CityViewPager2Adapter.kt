package com.kkw.smallweather.ui.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kkw.smallweather.ui.main.fragment.WeatherFragment

class CityViewPager2Adapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private val mWeatherFragment1 = WeatherFragment.newInstance("上海市")
    private val mWeatherFragment2 = WeatherFragment.newInstance("北京市")

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = when(position) {
            0 -> mWeatherFragment1
            1 -> mWeatherFragment2
            else -> {mWeatherFragment1}
        }
        return fragment
    }
}