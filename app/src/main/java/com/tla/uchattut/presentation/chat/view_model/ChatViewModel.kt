package com.tla.uchattut.presentation.chat.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tla.uchattut.data.repositories.chat.FakeChatRepository
import com.tla.uchattut.data.repositories.chat.models.MessageRepoModel
import com.tla.uchattut.domain.chat.ChatInteractor
import com.tla.uchattut.presentation._common.resources.ResourceManager

class ChatViewModel: ViewModel() {
    private val chatInteractor = ChatInteractor(FakeChatRepository())

    val messageList = MutableLiveData<List<MessageRepoModel>>()
    val state = MutableLiveData(State.EMPTY)

    fun requestMessages(userId: Int) {
        messageList.postValue(chatInteractor.getAllMessages())
    }

    enum class State {
        LOADING, EMPTY, CONTENT
    }
}