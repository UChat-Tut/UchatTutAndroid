package com.tla.uchattut.domain.dialogues

import com.tla.uchattut.data.repositories.dialogues.models.DialogueRepoModel
import kotlin.math.min

class DialoguesInteractor(
    private val repository: DialoguesRepository
) {
    suspend fun getChatList(): List<DialogueRepoModel> =
        repository.getChatList().map {
            limitMaxUnreadMessageCount(it)
            it
        }

    private fun limitMaxUnreadMessageCount(dialogues: DialogueRepoModel) {
        if (dialogues.unreadMessageCount != null) {
            dialogues.unreadMessageCount = min(999999, dialogues.unreadMessageCount!!)
        }
    }
}