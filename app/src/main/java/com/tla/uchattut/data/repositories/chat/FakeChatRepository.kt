package com.tla.uchattut.data.repositories.chat

import com.tla.uchattut.data.repositories.chat.models.MessageRepoModel
import com.tla.uchattut.domain.chat.ChatRepository

class FakeChatRepository : ChatRepository {
    override fun getAllMessages(): List<MessageRepoModel> = listOf(
        MessageRepoModel(),
        MessageRepoModel(),
        MessageRepoModel(),
        MessageRepoModel(),
        MessageRepoModel(),
        MessageRepoModel()
    )
}