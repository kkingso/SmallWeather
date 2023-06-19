package com.kkw.smallweather.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ScrollView
import kotlin.math.abs

/**
 * 支持嵌套的ScrollView
 */
class NestedScrollView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    ScrollView(context, attrs) {

    var mStartX = 0f
    var mStartY = 0f

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                mStartX = ev.x
                mStartY = ev.y
                parent.requestDisallowInterceptTouchEvent(true)
            }

            MotionEvent.ACTION_MOVE -> {
                val endX = ev.x
                val endY = ev.y
                val disX = abs(endX - mStartX)
                val disY = abs(endY - mStartY)
                if (disX > disY) {
                    parent.requestDisallowInterceptTouchEvent(canScrollHorizontally((mStartX - endX).toInt()))
                } else {
                    parent.requestDisallowInterceptTouchEvent(canScrollVertically((mStartY - endY).toInt()))
                }
            }

            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_CANCEL -> {
                parent.requestDisallowInterceptTouchEvent(true)
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}