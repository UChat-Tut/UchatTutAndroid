package com.tla.uchattut.presentation._common

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.Menu
import com.tla.uchattut.R

fun Menu.setIconsColor(color: Int) {
    for (i in 0 until size()) {
        val drawable: Drawable = getItem(i).icon
        drawable.mutate()
        drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
    }
}

fun Context.px(dp: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
}

fun Context.saveTextToClipboard(text: String) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
    val clip = ClipData.newPlainText(getString(R.string.app_name), text)
    clipboard?.setPrimaryClip(clip)
}