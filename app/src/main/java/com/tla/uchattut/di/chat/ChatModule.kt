package com.tla.uchattut.di.chat

import com.tla.uchattut.data.repositories.chat.FakeChatRepository
import com.tla.uchattut.domain.chat.ChatInteractor
import com.tla.uchattut.domain.chat.ChatRepository
import com.tla.uchattut.presentation._common.viewModel
import com.tla.uchattut.presentation.chat.view.ChatFragment
import com.tla.uchattut.presentation.chat.view_model.ChatViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [ChatModule.ChatAbstractModule::class])
class ChatModule(
    private val fragment: ChatFragment
) {

    @Provides
    fun provideViewModel(chatInteractor: ChatInteractor): ChatViewModel =
        fragment.viewModel { ChatViewModel(chatInteractor) }

    @Module
    interface ChatAbstractModule {
        @Binds
        @ChatScope
        fun bindChatRepository(chatRepository: FakeChatRepository): ChatRepository
    }
}