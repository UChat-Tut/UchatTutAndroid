package com.tla.uchattut.data.repositories.chat

import com.google.gson.Gson
import com.tla.uchattut.data.network.RestApi
import com.tla.uchattut.data.network.RetrofitClient
import com.tla.uchattut.data.network.WebSocketApi
import com.tla.uchattut.data.repositories.chat.models.ChatRepoModel
import com.tla.uchattut.data.repositories.chat.models.request.RequestMessageRepoModel
import com.tla.uchattut.data.repositories.chat.models.response.ResponseMessageRepoModel
import com.tla.uchattut.domain.chat.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class ChatRepositoryImpl : ChatRepository {

    private val networkApi: RestApi = RetrofitClient.networkApi
    private val gson = Gson()

    override suspend fun getChat(dialogueId: Int): ChatRepoModel {
        return networkApi.getChat(dialogueId)
    }

    override fun removeMessages(messages: List<ResponseMessageRepoModel>) {

    }

    override fun sendMessage(senderId: String, message: String) {
        val numSenderId = senderId.toInt()
        val requestMessage = RequestMessageRepoModel(numSenderId, message)
        val requestMessageJson = gson.toJson(requestMessage)

        WebSocketApi.sendMessage(requestMessageJson)
    }

    override fun connectDialogue(dialogueId: Int) {
        WebSocketApi.connectWebSocket(dialogueId)
    }

    override fun observeMessages(): Flow<ResponseMessageRepoModel> =
        WebSocketApi.observeMessages()
            .map { gson.fromJson(it, ResponseMessageRepoModel::class.java) }
}