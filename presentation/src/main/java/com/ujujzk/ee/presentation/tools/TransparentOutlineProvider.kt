package com.ujujzk.ee.presentation.tools

import android.graphics.Outline
import android.graphics.Path
import android.view.View
import android.view.ViewOutlineProvider


class TransparentOutlineProvider : ViewOutlineProvider() {
    override fun getOutline(view: View, outline: Outline) {

        val path = Path()

        path.moveTo(0f, view.height.toFloat())
        path.lineTo(view.width.toFloat(), view.height.toFloat())
        path.lineTo(view.width.toFloat(), view.height.toFloat()-1)
        path.lineTo(0f, view.height.toFloat()-1)
        path.close()

        outline.setConvexPath(path) //todo
    }
}