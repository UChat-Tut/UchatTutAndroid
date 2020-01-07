package com.tla.uchattut.domain.chat

import com.tla.uchattut.data.repositories.chat.models.ChatRepoModel
import com.tla.uchattut.data.repositories.chat.models.MessageRepoModel

interface ChatRepository {
    suspend fun getChat(dialogueId: Int): ChatRepoModel

    fun removeMessages(messages: List<MessageRepoModel>)
}