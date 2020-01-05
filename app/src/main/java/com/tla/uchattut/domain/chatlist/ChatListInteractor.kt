package com.tla.uchattut.domain.chatlist

import com.tla.uchattut.data.repositories.chatlist.models.ChatRepoModel

class ChatListInteractor(
    private val repository: ChatListRepository
) {
    fun getChatList(): List<ChatRepoModel> =
        repository.getChatList()
}