package com.example.sampletask.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.sampletask.R

object ExtensionFunctions {

    fun View.show() {
        this.visibility = View.VISIBLE
    }

    fun View.hide() {
        this.visibility = View.GONE
    }

    fun TextView.setTextViewBackground(context: Context, background: Int) {
        this.background =
            ContextCompat.getDrawable(context, background)
    }

    fun TextView.setLeftDrawable(image: Int) {
        this.setCompoundDrawablesWithIntrinsicBounds(image, 0, 0, 0);
    }

    fun ImageView.setSrcColor(context: Context, color: Int) {
        setColorFilter(
            ContextCompat.getColor(
                context,
                color
            ), android.graphics.PorterDuff.Mode.MULTIPLY
        )
    }

}