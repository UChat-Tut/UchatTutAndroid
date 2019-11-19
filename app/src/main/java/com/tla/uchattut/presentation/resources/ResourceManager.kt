package com.tla.uchattut.presentation.resources

import androidx.annotation.StringRes

interface ResourceManager {
    fun getString(@StringRes resId: Int): String
}