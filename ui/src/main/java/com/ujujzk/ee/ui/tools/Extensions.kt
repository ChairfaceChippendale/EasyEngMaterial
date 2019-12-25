package com.ujujzk.ee.ui.tools

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.*
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding


fun View.visible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

fun <T: ViewDataBinding> ViewGroup.inflate(@LayoutRes layoutRes: Int): T =
    DataBindingUtil.inflate<T>(LayoutInflater.from(context), layoutRes, this, false)



fun View.updatePadding(
    left: Int = paddingLeft,
    top: Int = paddingTop,
    right: Int = paddingRight,
    bottom: Int = paddingBottom
) {
     setPadding(left, top, right, bottom)
}

fun View.addSystemTopPadding(
    targetView: View = this,
    isConsumed: Boolean = false
) {
    doOnApplyWindowInsets { _, insets, initialPadding ->
        targetView.updatePadding(
            top = initialPadding.top + insets.systemWindowInsetTop
        )
        if (isConsumed) {
            insets.replaceSystemWindowInsets(
                Rect(
                    insets.systemWindowInsetLeft,
                    0,
                    insets.systemWindowInsetRight,
                    insets.systemWindowInsetBottom
                )
            )
        } else {
            insets
        }
    }
}
/**
 * @param isConsumed    {@code true} - it will handle inset and other views will not receive it,
 *                      {@code false} - it will handle inset and other views will also receive it
 * @param targetView    the view that will handle insets first??
 *
 */
fun View.addSystemBottomPadding(
    targetView: View = this,
    isConsumed: Boolean = false
) {
    doOnApplyWindowInsets { _, insets, initialPadding ->
        targetView.updatePadding(
            bottom = initialPadding.bottom + insets.systemWindowInsetBottom
        )
        if (isConsumed) {
            insets.replaceSystemWindowInsets(
                Rect(
                    insets.systemWindowInsetLeft,
                    insets.systemWindowInsetTop,
                    insets.systemWindowInsetRight,
                    0
                )
            )
        } else {
            insets
        }
    }
}

fun View.addSystemTopBottomPadding(
    targetView: View = this,
    isConsumed: Boolean = false
) {
    doOnApplyWindowInsets { _, insets, initialPadding ->
        targetView.updatePadding(
            bottom = initialPadding.bottom + insets.systemWindowInsetBottom,
            top = initialPadding.top + insets.systemWindowInsetTop
        )
        if (isConsumed) {
            insets.replaceSystemWindowInsets(
                Rect(
                    insets.systemWindowInsetLeft,
                    0,
                    insets.systemWindowInsetRight,
                    0
                )
            )
        } else {
            insets
        }
    }
}

fun View.addSystemBottomMargin(
    targetView: View = this,
    isConsumed: Boolean = false
) {
    doOnMarginApplyWindowInsets { _, insets, initialMargin ->

        val params = targetView.layoutParams as ViewGroup.MarginLayoutParams
        params.setMargins(
            initialMargin.left,
            initialMargin.top,
            initialMargin.right,
            initialMargin.bottom + insets.systemWindowInsetBottom
        )
        targetView.layoutParams = params

        if (isConsumed) {
            insets.replaceSystemWindowInsets(
                Rect(
                    insets.systemWindowInsetLeft,
                    insets.systemWindowInsetTop,
                    insets.systemWindowInsetRight,
                    0
                )
            )
        } else {
            insets
        }
    }
}

fun View.addSystemTopMargin(
    targetView: View = this,
    isConsumed: Boolean = false
) {
    doOnMarginApplyWindowInsets { _, insets, initialMargin ->

        val params = targetView.layoutParams as ViewGroup.MarginLayoutParams
        params.setMargins(
            initialMargin.left,
            initialMargin.top + insets.systemWindowInsetTop,
            initialMargin.right,
            initialMargin.bottom
        )
        targetView.layoutParams = params

        if (isConsumed){
            insets.replaceSystemWindowInsets(
                Rect(
                    insets.systemWindowInsetLeft,
                    0,
                    insets.stableInsetRight,
                    insets.systemWindowInsetBottom
                )
            )
        } else{
            insets
        }

    }
}

fun View.doOnMarginApplyWindowInsets(block: (View, insets: WindowInsetsCompat, initialMargin: Rect) -> WindowInsetsCompat) {

    val initialMargin = Rect(marginLeft, marginTop, marginRight, marginBottom)

    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        block(v, insets, initialMargin)
    }
    requestApplyInsetsWhenAttached()
}

fun View.doOnApplyWindowInsets(block: (View, insets: WindowInsetsCompat, initialPadding: Rect) -> WindowInsetsCompat) {
    val initialPadding = recordInitialPaddingForView(this)
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        block(v, insets, initialPadding)
    }
    requestApplyInsetsWhenAttached()
}

private fun recordInitialPaddingForView(view: View) =
    Rect(view.paddingLeft, view.paddingTop, view.paddingRight, view.paddingBottom)

private fun View.requestApplyInsetsWhenAttached() {
    if (isAttachedToWindow) {
        ViewCompat.requestApplyInsets(this)
    } else {
        addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                v.removeOnAttachStateChangeListener(this)
                ViewCompat.requestApplyInsets(v)
            }

            override fun onViewDetachedFromWindow(v: View) = Unit
        })
    }
}
