package com.tla.uchattut.domain.chatlist

import com.tla.uchattut.data.repositories.chatlist.models.ChatRepoModel

interface ChatListRepository {
    fun getChatList(): List<ChatRepoModel>
}