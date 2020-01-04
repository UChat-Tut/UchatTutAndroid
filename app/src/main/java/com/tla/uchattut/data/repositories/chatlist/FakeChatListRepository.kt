package com.tla.uchattut.data.repositories.chatlist

import com.tla.uchattut.data.repositories.chatlist.models.ChatRepoModel
import com.tla.uchattut.domain.chatlist.ChatListRepository

class FakeChatListRepository : ChatListRepository {
    override fun getChatList(): List<ChatRepoModel> {
        return listOf(
            ChatRepoModel(
                imageUrl = null,
                interlocutorName = "Дмитрий",
                lastMessage = "Привет",
                lastMessageTime = "12:10",
                unreadMessageCount = 1
            ),
            ChatRepoModel(
                imageUrl = null,
                interlocutorName = "Дмитрий",
                lastMessage = "Привет",
                lastMessageTime = "12:10",
                unreadMessageCount = 1
            ),
            ChatRepoModel(
                imageUrl = null,
                interlocutorName = "Дмитрий",
                lastMessage = "Привет",
                lastMessageTime = "12:10",
                unreadMessageCount = 1
            ),
            ChatRepoModel(
                imageUrl = null,
                interlocutorName = "Дмитрий",
                lastMessage = "Привет",
                lastMessageTime = "12:10",
                unreadMessageCount = 1
            )
        )
    }
}