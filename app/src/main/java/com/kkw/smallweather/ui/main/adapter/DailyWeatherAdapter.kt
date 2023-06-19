package com.kkw.smallweather.ui.main.adapter

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Icon
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.kkw.smallweather.R
import com.kkw.smallweather.bean.DailyBean
import com.kkw.smallweather.databinding.ItemDailyWeatherBinding
import com.kkw.smallweather.utils.ResourceUtil

class DailyWeatherAdapter: BaseQuickAdapter<DailyBean, DailyWeatherAdapter.ViewHolder>() {

    lateinit var mBinding: ItemDailyWeatherBinding

    class ViewHolder(binding: ItemDailyWeatherBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        mBinding = ItemDailyWeatherBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(mBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, item: DailyBean?) {
        mBinding.dailyDate.text = item?.fxDate
        mBinding.dailyMaxMinTemp.maxTemp.text = "${item?.tempMax}℃"
        mBinding.dailyMaxMinTemp.minTemp.text = "${item?.tempMin}℃"
        val img = "weather_${item?.iconDay}"
        mBinding.dailyIcon.setImageResource(ResourceUtil.getImageResId(context, img))

//        val typeFace = Typeface.createFromAsset(context.assets, "fonts/qweather-icons.ttf")
//        mBinding.dailyMaxTemp.typeface = typeFace

    }
}