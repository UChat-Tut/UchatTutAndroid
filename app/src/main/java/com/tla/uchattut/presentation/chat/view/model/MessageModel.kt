package com.tla.uchattut.presentation.chat.view.model

data class MessageModel(
    val id: String,
    val userName: String,
    val lastMessage: String,
    val imageUrl: String,
    val messageTime: String,
    val unreadMsgNumber: Int
)