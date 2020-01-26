package com.tla.uchattut.di.chat

import androidx.lifecycle.ViewModelProvider
import com.tla.uchattut.data.repositories.chat.FakeChatRepository
import com.tla.uchattut.domain.chat.ChatInteractor
import com.tla.uchattut.domain.chat.ChatRepository
import com.tla.uchattut.presentation._common.factory
import com.tla.uchattut.presentation.chat.ChatViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [ChatModule.ChatAbstractModule::class])
class ChatModule {

    @Provides
    @ChatScope
    fun provideViewModelFactory(chatInteractor: ChatInteractor): ViewModelProvider.Factory =
        factory { ChatViewModel(chatInteractor) }

    @Module
    interface ChatAbstractModule {
        @Binds
        fun bindChatRepository(chatRepository: FakeChatRepository): ChatRepository
    }
}