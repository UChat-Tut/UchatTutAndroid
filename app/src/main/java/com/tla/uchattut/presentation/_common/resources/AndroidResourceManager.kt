package com.tla.uchattut.presentation._common.resources

import android.content.Context
import androidx.annotation.StringRes
import javax.inject.Inject

class AndroidResourceManager @Inject constructor(
    private val context: Context?
) : ResourceManager {

    override fun getString(@StringRes resId: Int): String =
        context!!.getString(resId)

    override fun getStringArray(resId: Int): Array<String> =
        context!!.resources.getStringArray(resId)

    override fun getColor(colorId: Int): Int =
        context!!.resources.getColor(colorId)
}