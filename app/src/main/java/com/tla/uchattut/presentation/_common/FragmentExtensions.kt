package com.tla.uchattut.presentation._common

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment

fun Fragment.addBackNavigtionCallback(onBackPressedCallback: () -> Unit) {
    val callback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressedCallback()
            }
        }
    requireActivity().onBackPressedDispatcher.addCallback(this, callback)

}