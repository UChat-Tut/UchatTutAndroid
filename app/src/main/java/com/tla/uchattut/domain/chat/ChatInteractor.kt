package com.tla.uchattut.domain.chat

import com.tla.uchattut.data.repositories.auth.AuthRepository
import com.tla.uchattut.data.repositories.chat.models.MessageRepoModel
import com.tla.uchattut.presentation.chat.view_model.model.MessagePresentationModel

class ChatInteractor(
    private val chatRepository: ChatRepository,
    private val userRepository: AuthRepository
) {
    fun getAllMessages(): List<MessagePresentationModel> =
        chatRepository.getAllMessages().map {
            MessagePresentationModel(
                type = spotMessageType(it),
                id = it.id,
                senderId = it.senderId,
                text = it.text,
                time = it.time
            )
        }.markLastInBlockMessages()

    private fun List<MessagePresentationModel>.markLastInBlockMessages(): List<MessagePresentationModel> {
        for (i in 0 until size) {
            if (this[i].senderId == null) continue

            this[i].isLastMessageInBlock = i == size - 1 || this[i].senderId != this[i + 1].senderId
        }
        return this
    }

    private fun spotMessageType(messageRepoModel: MessageRepoModel): MessagePresentationModel.Type =
        when (messageRepoModel.senderId) {
            null -> MessagePresentationModel.Type.DATE
            userRepository.getCurrentUserId() -> MessagePresentationModel.Type.OUT
            else -> MessagePresentationModel.Type.IN
        }
}