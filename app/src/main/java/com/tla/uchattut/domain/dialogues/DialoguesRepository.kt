package com.tla.uchattut.domain.dialogues

import com.tla.uchattut.data.network.ResultWrapper
import com.tla.uchattut.data.repositories.dialogues.models.DialogueRepoModel

interface DialoguesRepository {
    suspend fun getChatList(): ResultWrapper<List<DialogueRepoModel>>
}