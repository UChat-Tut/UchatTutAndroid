package com.tla.uchattut.data.repositories.chatlist

import com.tla.uchattut.data.repositories.chatlist.models.ChatRepoModel
import com.tla.uchattut.domain.chatlist.ChatListRepository

class FakeChatListRepository : ChatListRepository {
    override fun getChatList(): List<ChatRepoModel> {
        return listOf(
            ChatRepoModel(
                imageUrl = "https://cdn2.iconfinder.com/data/icons/circle-avatars-1/128/050_girl_avatar_profile_woman_suit_student_officer-512.png",
                interlocutorName = "Дмитрий1",
                lastMessage = "Привет",
                lastMessageTime = "12:10",
                unreadMessageCount = 1
            ),
            ChatRepoModel(
                imageUrl = "https://cdn2.iconfinder.com/data/icons/circle-avatars-1/128/050_girl_avatar_profile_woman_suit_student_officer-512.png",
                interlocutorName = "Дмитрий2",
                lastMessage = "Привет",
                lastMessageTime = "12:10",
                unreadMessageCount = 1
            ),
            ChatRepoModel(
                imageUrl = "https://cdn2.iconfinder.com/data/icons/circle-avatars-1/128/050_girl_avatar_profile_woman_suit_student_officer-512.png",
                interlocutorName = "Дмитрий3",
                lastMessage = "Привет",
                lastMessageTime = "12:10",
                unreadMessageCount = 1
            ),
            ChatRepoModel(
                imageUrl = "https://cdn2.iconfinder.com/data/icons/circle-avatars-1/128/050_girl_avatar_profile_woman_suit_student_officer-512.png",
                interlocutorName = "Дмитрий4",
                lastMessage = "Привет",
                lastMessageTime = "12:10",
                unreadMessageCount = 1
            )
        )
    }
}