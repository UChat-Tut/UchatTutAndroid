package com.tla.uchattut.presentation._common

import androidx.fragment.app.Fragment

fun Fragment.toast(text: String) {
    context?.toast(text)
}