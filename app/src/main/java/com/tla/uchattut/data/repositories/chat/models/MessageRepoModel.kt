package com.tla.uchattut.data.repositories.chat.models

data class MessageRepoModel(
    val id: Int = 0,
    val senderId: String?,
    val text: String?,
    val time: String?
)