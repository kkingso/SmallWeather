package com.kkw.smallweather.ui.main.fragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.QuickAdapterHelper
import com.kkw.smallweather.bean.BaseWeather
import com.kkw.smallweather.bean.DailyBean
import com.kkw.smallweather.bean.HourlyBean
import com.kkw.smallweather.bean.NowBean
import com.kkw.smallweather.callback.RequestCallback
import com.kkw.smallweather.databinding.FragmentWeatherBinding
import com.kkw.smallweather.http.HttpRepository
import com.kkw.smallweather.http.HttpRepository.await
import com.kkw.smallweather.ui.main.activity.MainActivity
import com.kkw.smallweather.ui.main.adapter.DailyWeatherAdapter
import com.kkw.smallweather.ui.main.adapter.HourlyWeatherAdapter
import com.kkw.smallweather.utils.CommonUtil
import com.kkw.smallweather.utils.observeState
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val SELECT_CITY = "select_city"

/**
 * A simple [Fragment] subclass.
 * Use the [WeatherFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WeatherFragment : Fragment() {
    private val TAG = "WeatherFragment"
    lateinit var mBinding: FragmentWeatherBinding
    lateinit var mDailyAdapter: DailyWeatherAdapter
    lateinit var mHourlyAdapter: HourlyWeatherAdapter
    lateinit var mHelper: QuickAdapterHelper
    private var mSelectCityName: String? = null
    private var mLocationId: String? = null
    private var mOnTitleListener: OnTitleListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mSelectCityName = it.getString(SELECT_CITY)
            if ("上海市" == mSelectCityName) {
                mLocationId = "101021200"
            } else if ("北京市" == mSelectCityName) {
                mLocationId = "101010200"
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentWeatherBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showTitleCity(false)

        // 小时adapter
        mHourlyAdapter = HourlyWeatherAdapter()
        mBinding.hourlyRecyclerview.run {
            adapter = mHourlyAdapter
            layoutManager =
                LinearLayoutManager(this@WeatherFragment.context, RecyclerView.HORIZONTAL, false)
        }
        // 每天adapter
        mDailyAdapter = DailyWeatherAdapter()
        mBinding.dailyRecyclerview.run {
            adapter = mDailyAdapter
            layoutManager =
                LinearLayoutManager(this@WeatherFragment.context, RecyclerView.VERTICAL, false)
        }

        // 标题栏动态计算位置
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

        // 天气下拉刷新
        mBinding.mainRefresh.apply {
            setOnRefreshListener {
                initData()
                isRefreshing = false
            }

        }

        // 初始化数据
        initData()
    }

    private fun initData() {
//        CoroutineScope(Dispatchers.Main).launch {
//            async(Dispatchers.IO) {  }
//        }
//        getNowWeather3()
        getNowWeather()
        getHourlyWeather()
        getDailyWeather()
    }

    private fun getNowWeather() {
        HttpRepository.weatherService.getWeatherNow(mLocationId)
            .await(object : RequestCallback<NowBean> {

                override fun onSuccess(data: BaseWeather<NowBean>?) {
                    data?.bean?.let { now ->
                        mBinding.nowTemp.text = now.temp
                        mBinding.nowText.text = now.text
                    }
//                    data?.updateTime?.let {
//                        mBinding.weatherUpdatetime.text = "上次更新时间：${it.substring(11..15)}"
//                    }
                    mBinding.weatherUpdatetime.text =
                        "上次更新时间：${CommonUtil.convertData(System.currentTimeMillis())}"
                }

                override fun onFailure(t: Throwable) {
                }
            })
    }


    private fun getNowWeather2() {
        val TAG = "kkkw"
        HttpRepository.weatherService.getWeatherNow2(mLocationId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseWeather<NowBean>> {
                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribe: $d")
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "onError: $e")
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete: ")
                }

                override fun onNext(t: BaseWeather<NowBean>) {
                    Log.d(TAG, "onNext: ${t}")
                }

            })
    }

    private fun getNowWeather3() {
        val TAG = "kkkw"
//        HttpRepository.weatherService.getWeatherNow3(mLocationId)
//            .observe(viewLifecycleOwner) {
//                Log.d(TAG, "getNowWeather3: $it")
//            }
        HttpRepository.weatherService.getWeatherNow3(mLocationId)
            .observeState(this,
                {
                    Log.d(TAG, "getNowWeather3: $this")
                },
                {

                })
    }

    private fun getHourlyWeather() {
        HttpRepository.weatherService.getWeatherHourly(24, mLocationId)
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
        HttpRepository.weatherService.getWeatherDaily(7, mLocationId)
            .await(object : RequestCallback<MutableList<DailyBean>> {

                override fun onSuccess(data: BaseWeather<MutableList<DailyBean>>?) {
                    Log.d("kkkw", "getDailyWeather: " + Thread.currentThread().name)
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

    private fun showTitleCity(showTitle: Boolean) {
        mOnTitleListener?.changeTitleName(mSelectCityName, showTitle)
//        mBinding.toolbarTitle.text = mNowCityName
        mBinding.nowCity.text = mSelectCityName
        if (showTitle) {
//            mBinding.toolbarTitle.visibility = View.VISIBLE
            mBinding.nowCity.visibility = View.INVISIBLE
        } else {
//            mBinding.toolbarTitle.visibility = View.INVISIBLE
            mBinding.nowCity.visibility = View.VISIBLE
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WeatherFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(city: String) =
            WeatherFragment().apply {
                arguments = Bundle().apply {
                    putString(SELECT_CITY, city)
                }
            }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mOnTitleListener = context as? MainActivity
    }

    override fun onDetach() {
        super.onDetach()
        mOnTitleListener = null
    }

    interface OnTitleListener {

        fun changeTitleName(name: String?, showTitle: Boolean)
    }
}