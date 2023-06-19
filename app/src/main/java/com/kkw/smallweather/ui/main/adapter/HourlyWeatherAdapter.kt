package com.kkw.smallweather.ui.main.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.kkw.smallweather.bean.HourlyBean
import com.kkw.smallweather.databinding.ItemHourlyWeatherBinding
import com.kkw.smallweather.utils.ResourceUtil
import kotlin.math.log

class HourlyWeatherAdapter: BaseQuickAdapter<HourlyBean, HourlyWeatherAdapter.ViewHolder>() {

    lateinit var mBinding: ItemHourlyWeatherBinding

    class ViewHolder(binding: ItemHourlyWeatherBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        mBinding = ItemHourlyWeatherBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(mBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, item: HourlyBean?) {
        mBinding.hourlyTime.text = item?.fxTime?.substring(11..15)
        mBinding.hourlyPercent.text = "${item?.pop}%"
        mBinding.hourlyPercent.visibility = if (item?.pop.isNullOrEmpty()) View.INVISIBLE else View.VISIBLE
        mBinding.hourlyTemp.text = "${item?.temp}℃"
        val img = "weather_${item?.icon}"
        mBinding.hourlyIcon.setImageResource(ResourceUtil.getImageResId(context, img))
//        mBinding.horlySpace.visibility = if (position == itemCount-1) View.VISIBLE else View.GONE
    }

}