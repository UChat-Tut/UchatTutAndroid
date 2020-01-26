package com.tla.uchattut.presentation._common.resources

import androidx.annotation.ArrayRes
import androidx.annotation.ColorRes
import androidx.annotation.StringRes

interface ResourceManager {

    fun getString(@StringRes resId: Int): String

    fun getStringArray(@ArrayRes resId: Int): Array<String>

    fun getColor(@ColorRes colorId: Int): Int
}