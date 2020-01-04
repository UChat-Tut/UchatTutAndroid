package com.tla.uchattut.domain.chatlist

import com.tla.uchattut.data.repositories.chatlist.models.ChatListRepoModel

class ChatListInteractor(
    private val repository: ChatListRepository
) {
    fun getChatList(): List<ChatListRepoModel> =
        repository.getChatList()
}