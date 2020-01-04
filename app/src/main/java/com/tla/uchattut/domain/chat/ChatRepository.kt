package com.tla.uchattut.domain.chat

import com.tla.uchattut.data.repositories.chat.models.MessageRepoModel

interface ChatRepository {
    fun getAllMessages(): List<MessageRepoModel>
}