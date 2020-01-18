package com.tla.uchattut.di.dialogues

import com.tla.uchattut.di.DaggerComponent
import com.tla.uchattut.di.app.AppComponent
import com.tla.uchattut.presentation.conversation.dialogues.view.DialoguesFragment
import dagger.Component

@DialoguesScope
@Component(modules = [DialoguesModule::class], dependencies = [AppComponent::class])
interface DialoguesComponent : DaggerComponent {
    fun inject(dialoguesFragment: DialoguesFragment)
}