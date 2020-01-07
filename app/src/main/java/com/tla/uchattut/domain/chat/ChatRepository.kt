package com.tla.uchattut.domain.chat

import com.tla.uchattut.data.repositories.chat.models.ChatRepoModel
import com.tla.uchattut.data.repositories.chat.models.response.ResponseMessageRepoModel
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun getChat(dialogueId: Int): ChatRepoModel

    fun removeMessages(messages: List<ResponseMessageRepoModel>)

    fun sendMessage(senderId: String, message: String)

    fun connectDialogue(dialogueId: Int)

    fun observeMessages(): Flow<ResponseMessageRepoModel>
}