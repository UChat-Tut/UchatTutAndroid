package com.tla.uchattut.data.repositories.chatlist.models

data class ChatRepoModel (
    val id: Int = 0,
    val imageUrl: String? = null,
    val interlocutorName: String = "",
    val lastMessage: String? = null,
    val lastMessageTime: String? = null,
    val unreadMessageCount: Int? = null
)