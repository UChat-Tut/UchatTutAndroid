package com.tla.uchattut.domain.dialogues

import com.tla.uchattut.data.repositories.dialogues.models.DialoguesRepoModel

interface DialoguesRepository {
    suspend fun getChatList(): List<DialoguesRepoModel>
}