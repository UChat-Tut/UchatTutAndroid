package com.tla.uchattut.presentation._common

import androidx.lifecycle.ViewModel
import com.tla.uchattut.di.DaggerComponent
import com.tla.uchattut.di.DaggerContainer
import kotlin.reflect.KClass

open class ScopeViewModel(
    private val componentClass: KClass<out DaggerComponent>
) : ViewModel() {
    override fun onCleared() {
        super.onCleared()
        DaggerContainer.clear(componentClass)
    }
}