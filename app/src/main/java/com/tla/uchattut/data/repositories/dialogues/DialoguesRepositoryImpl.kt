package com.tla.uchattut.data.repositories.dialogues

import com.tla.uchattut.data.network.DialoguesService
import com.tla.uchattut.data.network.RetrofitClient
import com.tla.uchattut.data.repositories.dialogues.models.DialogueRepoModel
import com.tla.uchattut.domain.dialogues.DialoguesRepository

class DialoguesRepositoryImpl : DialoguesRepository {

    private val dialoguesService: DialoguesService =
        RetrofitClient.retrofit.create(DialoguesService::class.java)

    override suspend fun getChatList(): List<DialogueRepoModel> =
        dialoguesService.getChats().dialogues!!
}