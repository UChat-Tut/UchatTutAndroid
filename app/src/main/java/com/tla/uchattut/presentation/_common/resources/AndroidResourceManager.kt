package com.tla.uchattut.presentation._common.resources

import android.content.Context
import androidx.annotation.StringRes

class AndroidResourceManager(
    private val context: Context
) : ResourceManager {
    override fun getString(@StringRes resId: Int): String =
        context.getString(resId)
}