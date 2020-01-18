package com.tla.uchattut.presentation._common

import androidx.fragment.app.Fragment

open class BaseFragment : Fragment(), BackPressable {
    override fun onBackPressed(): Boolean {
        val backPressableFragments = childFragmentManager.fragments
            .filterIsInstance(BackPressable::class.java)

        for (fragment in backPressableFragments) {
            if (fragment.onBackPressed()) {
                return true
            }
        }
        return false
    }
}