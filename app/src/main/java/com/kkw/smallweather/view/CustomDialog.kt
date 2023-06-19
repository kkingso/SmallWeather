package com.kkw.smallweather.view

import android.app.Dialog
import android.content.Context
import androidx.annotation.NonNull
import androidx.annotation.StyleRes

abstract class CustomDialog @JvmOverloads constructor(@NonNull context: Context, @StyleRes themeResId: Int = 0): Dialog(context, themeResId) {

    init {
        this.setContentView(getDialogLayout())
    }

    abstract fun getDialogLayout(): Int

    override fun show() {
        super.show()
    }
}