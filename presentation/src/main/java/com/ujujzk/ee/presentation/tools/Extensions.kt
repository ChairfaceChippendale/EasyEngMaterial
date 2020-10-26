package com.ujujzk.ee.presentation.tools

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.*


fun View.visible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}


fun View.onClick(block: () -> Unit) = setOnClickListener { block.invoke() }

@JvmName("postUpdateLayoutParamsTyped")
inline fun <reified T : ViewGroup.LayoutParams> View.postUpdateLayoutParams(crossinline block: T.() -> Unit) {
    post {
        val params = layoutParams as T
        block(params)
        layoutParams = params
    }
}
