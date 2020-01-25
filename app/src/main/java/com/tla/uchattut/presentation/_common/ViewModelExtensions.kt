package com.tla.uchattut.presentation._common

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

inline fun <reified T : ViewModel> Fragment.viewModel(crossinline f: () -> T): T {
    return ViewModelProviders.of(
        this,
        factory(f)
    ).get(T::class.java)
}

inline fun <reified T : ViewModel> FragmentActivity.viewModel(crossinline f: () -> T): T {
    return ViewModelProviders.of(
        this,
        factory(f)
    ).get(T::class.java)
}

@Suppress("UNCHECKED_CAST")
inline fun <VM : ViewModel> factory(crossinline f: () -> VM) = object : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return f() as T
    }
}