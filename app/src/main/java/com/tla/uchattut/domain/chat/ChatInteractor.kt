package com.tla.uchattut.domain.chat

import com.tla.uchattut.data.repositories.chat.models.MessageRepoModel

class ChatInteractor(
    private val chatRepository: ChatRepository
) {
    fun getAllMessages(): List<MessageRepoModel> =
        chatRepository.getAllMessages()
}