package com.tla.uchattut.domain.chat

import com.tla.uchattut.data.repositories.auth.AuthRepository
import com.tla.uchattut.data.repositories.chat.models.response.ResponseMessageRepoModel
import com.tla.uchattut.presentation.chat.view_model.model.ChatPresentationModel
import com.tla.uchattut.presentation.chat.view_model.model.MessagePresentationModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ChatInteractor(
    private val chatRepository: ChatRepository,
    private val userRepository: AuthRepository
) {
    suspend fun getChat(dialogueId: Int): ChatPresentationModel {
        val chatRepoModel = chatRepository.getChat(dialogueId)

        val messages = chatRepoModel.messages.map {
            it.convertToMessagePresentationModel()
        }.markLastInBlockMessages()

        return ChatPresentationModel(messages)
    }

    private fun ResponseMessageRepoModel.convertToMessagePresentationModel(): MessagePresentationModel =
        MessagePresentationModel(
            type = spotMessageType(this),
            id = id ?: 0,
            senderId = sender,
            text = message,
            time = time
        )

    private fun List<MessagePresentationModel>.markLastInBlockMessages(): List<MessagePresentationModel> {
        for (i in 0 until size) {
            if (this[i].senderId == null) continue

            this[i].isLastMessageInBlock = i == size - 1 || this[i].senderId != this[i + 1].senderId
        }
        return this
    }

    private fun spotMessageType(messageRepoModel: ResponseMessageRepoModel): MessagePresentationModel.Type =
        when (messageRepoModel.sender) {
            null -> MessagePresentationModel.Type.DATE
            userRepository.getCurrentUserId() -> MessagePresentationModel.Type.OUT
            else -> MessagePresentationModel.Type.IN
        }

    fun removeMessages(messages: List<MessagePresentationModel>) {
        val repoMessages = messages.map {
            ResponseMessageRepoModel(
                id = it.id,
                sender = it.senderId,
                message = it.text,
                time = it.time
            )
        }
        chatRepository.removeMessages(repoMessages)
    }

    fun sendMessage(senderId: String, message: String) {
        chatRepository.sendMessage(senderId, message)
    }

    fun connectDialogue(dialogueId: Int) {
        chatRepository.connectDialogue(dialogueId)
    }

    fun extractTextFromMessages(messages: List<MessagePresentationModel>): String {

        var currentUserId = ""
        val stringBuilder = StringBuilder()

        messages.forEach {
            if (it.senderId != currentUserId) {
                stringBuilder.append("${it.senderId}:\n")
                currentUserId = it.senderId!!
            }
            stringBuilder.append("${it.text}\n\n")
        }
        return stringBuilder.toString().trim()
    }

    fun observeMessages(): Flow<MessagePresentationModel> =
        chatRepository.observeMessages()
            .map {
                it.convertToMessagePresentationModel()
            }
}