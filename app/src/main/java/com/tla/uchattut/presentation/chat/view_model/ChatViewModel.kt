package com.tla.uchattut.presentation.chat.view_model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tla.uchattut.data.repositories.auth.AuthRepository
import com.tla.uchattut.data.repositories.chat.ChatRepositoryImpl
import com.tla.uchattut.data.repositories.chat.FakeChatRepository
import com.tla.uchattut.domain.chat.ChatInteractor
import com.tla.uchattut.presentation._common.saveTextToClipboard
import com.tla.uchattut.presentation.chat.view_model.model.ChatPresentationModel
import com.tla.uchattut.presentation.chat.view_model.model.MessagePresentationModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ChatViewModel : ViewModel() {
    private val chatInteractor =
        ChatInteractor(FakeChatRepository(AuthRepository()), AuthRepository())

    private val chatLiveData = MutableLiveData<ChatPresentationModel>()
    val state = MutableLiveData<State>()

    fun getChatLiveData(): LiveData<ChatPresentationModel> = chatLiveData

    fun requestChat(dialogueId: Int) = viewModelScope.launch(Dispatchers.IO) {

        state.postValue(State.LOADING)

        val messages = chatInteractor.getChat(dialogueId)
        chatLiveData.postValue(messages)

        if (messages.messages.isNullOrEmpty()) {
            state.postValue(State.EMPTY)
        } else {
            state.postValue(State.CONTENT)
        }
    }

    fun sendMessage(message: String) {
        chatInteractor.sendMessage("123", message)
    }

    fun removeMessages(messages: List<MessagePresentationModel>) {
        chatInteractor.removeMessages(messages)
    }

    fun copyMessages(context: Context?, messages: List<MessagePresentationModel>) {
        val text = chatInteractor.extractTextFromMessages(messages)
        context?.saveTextToClipboard(text)
    }

    fun connectDialogue(dialogueId: Int) {
        chatInteractor.connectDialogue(dialogueId)
    }

    enum class State {
        LOADING, EMPTY, CONTENT
    }
}