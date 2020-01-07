package com.tla.uchattut.data.repositories.chat

import com.tla.uchattut.data.repositories.auth.AuthRepository
import com.tla.uchattut.data.repositories.chat.models.MessageRepoModel
import com.tla.uchattut.domain.chat.ChatRepository

class FakeChatRepository(
    private val authRepository: AuthRepository
): ChatRepository {

    override fun getAllMessages(): List<MessageRepoModel> = listOf(
        MessageRepoModel(
            id = 0,
            senderId = authRepository.getCurrentUserId(),
            text = "Привет! ",
            time = "10:01"
        ),
        MessageRepoModel(
            id = 1,
            senderId = "GHse4rqyPoaSdfVre037x7vWf2sc",
            text = "Привет. ",
            time = "10:02"
        ),
        MessageRepoModel(
            id = 2,
            senderId = authRepository.getCurrentUserId(),
            text = "Оплати занятие по номеру +7(985)333-33-33",
            time = "10:05"
        ),
        MessageRepoModel(
            id = -1,
            senderId = null,
            text = null,
            time = "Вторник"
        ),
        MessageRepoModel(
            id = 3,
            senderId = "GHse4rqyPoaSdfVre037x7vWf2sc",
            text = "Хорошо",
            time = "10:06"
        ),
        MessageRepoModel(
            id = 4,
            senderId = "GHse4rqyPoaSdfVre037x7vWf2sc",
            text = "Оплатил",
            time = "10:10"
        )
    )

    override fun removeMessages(messages: List<MessageRepoModel>) {

    }
}