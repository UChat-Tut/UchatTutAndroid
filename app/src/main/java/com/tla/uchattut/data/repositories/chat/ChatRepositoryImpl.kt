package com.tla.uchattut.data.repositories.chat

import com.tla.uchattut.data.network.ChatService
import com.tla.uchattut.data.network.RetrofitClient
import com.tla.uchattut.data.repositories.chat.models.ChatRepoModel
import com.tla.uchattut.data.repositories.chat.models.MessageRepoModel
import com.tla.uchattut.domain.chat.ChatRepository

class ChatRepositoryImpl : ChatRepository {

    private val chatService: ChatService = RetrofitClient.retrofit.create(ChatService::class.java)

    override suspend fun getChat(dialogueId: Int): ChatRepoModel {
        return chatService.getChat(dialogueId)
    }

    override fun removeMessages(messages: List<MessageRepoModel>) {
    }
}