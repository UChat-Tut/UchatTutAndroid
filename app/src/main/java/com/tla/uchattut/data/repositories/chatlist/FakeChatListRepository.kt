package com.tla.uchattut.data.repositories.chatlist

import com.tla.uchattut.data.repositories.chatlist.models.ChatListRepoModel
import com.tla.uchattut.domain.chatlist.ChatListRepository

class FakeChatListRepository : ChatListRepository {
    override fun getChatList(): List<ChatListRepoModel> {
        return listOf(
            ChatListRepoModel(
                imageUrl = null,
                interlocutorName = "Дмитрий",
                lastMessage = "Привет",
                lastMessageTime = "12:10",
                unreadMessageCount = 1
            ),
            ChatListRepoModel(
                imageUrl = null,
                interlocutorName = "Дмитрий",
                lastMessage = "Привет",
                lastMessageTime = "12:10",
                unreadMessageCount = 1
            ),
            ChatListRepoModel(
                imageUrl = null,
                interlocutorName = "Дмитрий",
                lastMessage = "Привет",
                lastMessageTime = "12:10",
                unreadMessageCount = 1
            ),
            ChatListRepoModel(
                imageUrl = null,
                interlocutorName = "Дмитрий",
                lastMessage = "Привет",
                lastMessageTime = "12:10",
                unreadMessageCount = 1
            )
        )
    }
}