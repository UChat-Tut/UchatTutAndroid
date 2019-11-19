package com.tla.uchattut.presentation.resources

import android.content.Context
import androidx.annotation.StringRes

class AndroidResourceManager(
    private val context: Context
) : ResourceManager {
    override fun getString(@StringRes resId: Int): String =
        context.getString(resId)
}