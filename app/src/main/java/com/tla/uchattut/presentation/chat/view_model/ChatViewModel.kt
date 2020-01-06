package com.tla.uchattut.presentation.chat.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tla.uchattut.data.repositories.auth.AuthRepository
import com.tla.uchattut.data.repositories.chat.FakeChatRepository
import com.tla.uchattut.domain.chat.ChatInteractor
import com.tla.uchattut.presentation.chat.view_model.model.MessagePresentationModel

class ChatViewModel: ViewModel() {
    private val chatInteractor = ChatInteractor(FakeChatRepository(), AuthRepository())

    val messageList = MutableLiveData<List<MessagePresentationModel>>()
    val state = MutableLiveData<State>()

    fun requestMessages(userId: Int) {
        state.postValue(State.LOADING)

        val messages = chatInteractor.getAllMessages()
        messageList.postValue(messages)

        if (messages.isNullOrEmpty()) {
            state.postValue(State.EMPTY)
        } else {
            state.postValue(State.CONTENT)
        }
    }

    fun sendMessage(text: String) {

    }

    enum class State {
        LOADING, EMPTY, CONTENT
    }
}