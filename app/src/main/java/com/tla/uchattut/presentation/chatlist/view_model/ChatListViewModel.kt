package com.tla.uchattut.presentation.chatlist.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tla.uchattut.data.repositories.chatlist.FakeChatListRepository
import com.tla.uchattut.data.repositories.chatlist.models.ChatRepoModel
import com.tla.uchattut.domain.chatlist.ChatListInteractor

class ChatListViewModel : ViewModel() {
    private val chatListInteractor = ChatListInteractor(FakeChatListRepository())

    val chatList: MutableLiveData<List<ChatRepoModel>> = MutableLiveData()
    val state: MutableLiveData<State> = MutableLiveData(State.EMPTY)

    fun requestChatList() {
        state.postValue(State.LOADING)

        val chats = chatListInteractor.getChatList()
        chatList.postValue(chats)

        if (chats.isNullOrEmpty()) {
            state.postValue(State.EMPTY)
        } else {
            state.postValue(State.CONTENT)
        }
    }

    enum class State {
        LOADING, CONTENT, EMPTY
    }
}