package com.tla.uchattut.domain.chatlist

import com.tla.uchattut.data.repositories.chatlist.models.ChatListRepoModel

interface ChatListRepository {
    fun getChatList(): List<ChatListRepoModel>
}