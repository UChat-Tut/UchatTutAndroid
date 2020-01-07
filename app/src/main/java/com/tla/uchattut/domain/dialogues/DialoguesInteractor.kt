package com.tla.uchattut.domain.dialogues

import com.tla.uchattut.data.repositories.dialogues.models.DialoguesRepoModel

class DialoguesInteractor(
    private val repository: DialoguesRepository
) {
    suspend fun getChatList(): List<DialoguesRepoModel> =
        repository.getChatList()
}