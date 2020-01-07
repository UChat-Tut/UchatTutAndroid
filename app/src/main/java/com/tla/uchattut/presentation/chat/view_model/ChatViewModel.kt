package com.tla.uchattut.presentation.chat.view_model

import android.content.Context
import androidx.lifecycle.*
import com.tla.uchattut.data.repositories.auth.AuthRepository
import com.tla.uchattut.data.repositories.chat.FakeChatRepository
import com.tla.uchattut.data.repositories.chat.models.ChatRepoModel
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

    fun sendMessage(text: String) {

    }

    fun removeMessages(messages: List<MessagePresentationModel>) {
        chatInteractor.removeMessages(messages)
    }

    fun copyMessages(context: Context?, messages: List<MessagePresentationModel>) {
        val text = chatInteractor.extractTextFromMessages(messages)
        context?.saveTextToClipboard(text)
    }

    enum class State {
        LOADING, EMPTY, CONTENT
    }
}