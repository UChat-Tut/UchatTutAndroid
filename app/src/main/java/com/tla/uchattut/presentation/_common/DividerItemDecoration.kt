package com.tla.uchattut.presentation._common

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView

class DividerItemDecoration(
    private val dividerDrawable: Drawable
) : RecyclerView.ItemDecoration() {

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        val childCount = parent.childCount

        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom: Int = top + dividerDrawable.intrinsicHeight
            dividerDrawable.setBounds(left, top, right, bottom)
            dividerDrawable.draw(canvas)
        }
    }
}