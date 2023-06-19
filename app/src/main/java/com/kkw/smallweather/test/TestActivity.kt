package com.kkw.smallweather.test

import android.view.View
import com.kkw.smallweather.BaseActivity
import com.kkw.smallweather.databinding.ActivityTestBinding

class TestActivity: BaseActivity() {
    lateinit var mBinding: ActivityTestBinding
    override fun getLayoutId(): View {
        mBinding = ActivityTestBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun initView() {

    }
}