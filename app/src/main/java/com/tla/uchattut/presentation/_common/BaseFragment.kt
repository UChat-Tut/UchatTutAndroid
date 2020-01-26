package com.tla.uchattut.presentation._common

import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {

    open fun onBackPressed(): Boolean {
        val backPressableFragments = childFragmentManager.fragments
            .filterIsInstance(BaseFragment::class.java)

        for (fragment in backPressableFragments) {
            if (fragment.onBackPressed()) {
                return true
            }
        }
        return false
    }
}