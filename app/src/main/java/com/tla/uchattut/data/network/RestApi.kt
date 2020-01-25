package com.tla.uchattut.data.network

import com.tla.uchattut.data.network.model.MinimalUserNetworkModel
import com.tla.uchattut.data.repositories.chat.models.ChatRepoModel
import com.tla.uchattut.data.repositories.dialogues.models.DialogueRepoModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestApi {
    @GET("chat/{dialogueId}")
    suspend fun getChat(@Path("dialogueId") dialogueId: Int): ChatRepoModel

    @GET("chat/")
    suspend fun getChats(): List<DialogueRepoModel>

    @GET("user/")
    suspend fun fetchUsers(@Query("q") query: String): List<MinimalUserNetworkModel>

}