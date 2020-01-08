package com.tla.uchattut.data.network

import com.tla.uchattut.data.repositories.chat.models.ChatRepoModel
import com.tla.uchattut.data.repositories.dialogues.models.DialoguesListRepoModel
import com.tla.uchattut.domain.chat.ChatRepository
import retrofit2.http.GET
import retrofit2.http.Path

interface ChatService {
    @GET("chat/{dialogueId}")
    suspend fun getChat(@Path("dialogueId") dialogueId: Int): ChatRepoModel
}