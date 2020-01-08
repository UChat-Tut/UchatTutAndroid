package com.tla.uchattut.data.repositories.chat

import com.tla.uchattut.data.repositories.auth.AuthRepository
import com.tla.uchattut.data.repositories.chat.models.ChatRepoModel
import com.tla.uchattut.data.repositories.chat.models.response.ResponseMessageRepoModel
import com.tla.uchattut.domain.chat.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Suppress("BlockingMethodInNonBlockingContext")
class FakeChatRepository(
    private val authRepository: AuthRepository
) : ChatRepository {

    override suspend fun getChat(dialogueId: Int): ChatRepoModel {
        Thread.sleep(2000)
        return ChatRepoModel(
            listOf(
                ResponseMessageRepoModel(
                    id = 0,
                    sender = authRepository.getCurrentUserId(),
                    message = "Привет! ",
                    time = "10:01"
                ),
                ResponseMessageRepoModel(
                    id = 1,
                    sender = "GHse4rqyPoaSdfVre037x7vWf2sc",
                    message = "Привет. ",
                    time = "10:02"
                ),
                ResponseMessageRepoModel(
                    id = 2,
                    sender = authRepository.getCurrentUserId(),
                    message = "Оплати занятие по номеру +7(985)333-33-33",
                    time = "10:05"
                ),
                ResponseMessageRepoModel(
                    id = -1,
                    sender = null,
                    message = null,
                    time = "Вторник"
                ),
                ResponseMessageRepoModel(
                    id = 3,
                    sender = "GHse4rqyPoaSdfVre037x7vWf2sc",
                    message = "Хорошо",
                    time = "10:06"
                ),
                ResponseMessageRepoModel(
                    id = 4,
                    sender = "GHse4rqyPoaSdfVre037x7vWf2sc",
                    message = "Оплатил",
                    time = "10:10"
                )
            )
        )
    }

    override fun removeMessages(messages: List<ResponseMessageRepoModel>) {
    }

    override fun sendMessage(senderId: String, message: String) {
    }

    override fun connectDialogue(dialogueId: Int) {
    }

    override fun observeMessages(): Flow<ResponseMessageRepoModel> = flow{}
}