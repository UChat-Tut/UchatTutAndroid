package com.tla.uchattut.presentation._common

import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.view.Menu

fun Menu.setIconsColor(color: Int) {
    for (i in 0 until size()) {
        val drawable: Drawable = getItem(i).icon
        drawable.mutate()
        drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
    }
}