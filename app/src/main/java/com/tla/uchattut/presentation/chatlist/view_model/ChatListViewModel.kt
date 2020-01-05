package com.tla.uchattut.presentation.chatlist.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tla.uchattut.data.repositories.chatlist.FakeChatListRepository
import com.tla.uchattut.data.repositories.chatlist.models.ChatListRepoModel
import com.tla.uchattut.domain.chatlist.ChatListInteractor

class ChatListViewModel : ViewModel() {
    private val chatListInteractor = ChatListInteractor(FakeChatListRepository())

    val chatList: MutableLiveData<List<ChatListRepoModel>> = MutableLiveData()
    val state: MutableLiveData<State> = MutableLiveData(State.EMPTY)

    fun requestChatList() {
        chatList.postValue(chatListInteractor.getChatList())
        state.postValue(State.CONTENT)
    }

    enum class State {
        LOADING, CONTENT, EMPTY
    }
}