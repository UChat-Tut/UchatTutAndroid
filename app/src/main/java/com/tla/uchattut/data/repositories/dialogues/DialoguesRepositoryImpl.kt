package com.tla.uchattut.data.repositories.dialogues

import com.tla.uchattut.data.network.RestApi
import com.tla.uchattut.data.network.RetrofitClient
import com.tla.uchattut.data.repositories.dialogues.models.DialogueRepoModel
import com.tla.uchattut.domain.dialogues.DialoguesRepository

class DialoguesRepositoryImpl : DialoguesRepository {

    private val networkApi: RestApi = RetrofitClient.networkApi

    override suspend fun getChatList(): List<DialogueRepoModel> =
        networkApi.getChats().dialogues!!
}