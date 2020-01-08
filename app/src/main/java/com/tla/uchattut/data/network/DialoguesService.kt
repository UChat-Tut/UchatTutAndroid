package com.tla.uchattut.data.network

import com.tla.uchattut.data.repositories.dialogues.models.DialoguesListRepoModel
import retrofit2.http.GET


interface DialoguesService {
    @GET("chat/")
    suspend fun getChats(): DialoguesListRepoModel
}