package com.tla.uchattut.di.chat

import com.tla.uchattut.di.DaggerComponent
import com.tla.uchattut.di.app.AppComponent
import com.tla.uchattut.di.auth.AuthComponent
import com.tla.uchattut.presentation.chat.view.ChatFragment
import dagger.Component

@ChatScope
@Component(modules = [ChatModule::class], dependencies = [AppComponent::class])
interface ChatComponent : DaggerComponent {
    fun inject(chatFragment: ChatFragment)
}