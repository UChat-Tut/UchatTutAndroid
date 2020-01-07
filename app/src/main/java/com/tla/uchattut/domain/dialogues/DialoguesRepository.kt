package com.tla.uchattut.domain.dialogues

import com.tla.uchattut.data.repositories.dialogues.models.DialogueRepoModel

interface DialoguesRepository {
    suspend fun getChatList(): List<DialogueRepoModel>
}