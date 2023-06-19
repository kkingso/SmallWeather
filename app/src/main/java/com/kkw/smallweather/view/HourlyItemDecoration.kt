package com.kkw.smallweather.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kkw.smallweather.utils.dp2px

class HourlyItemDecoration: RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
//        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = 0
        outRect.bottom = 0
        when(parent.getChildAdapterPosition(view)) {
            0 -> {
                outRect.left = 30.dp2px()
                outRect.right = 5.dp2px()
            }
            else -> {
                outRect.left = 5.dp2px()
                outRect.right = 5.dp2px()
            }
        }
    }
}