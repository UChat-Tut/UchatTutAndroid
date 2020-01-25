package com.tla.uchattut.domain.dialogues

import com.tla.uchattut.data.network.ResultWrapper
import com.tla.uchattut.data.repositories.dialogues.models.DialogueRepoModel
import javax.inject.Inject
import kotlin.math.min

class DialoguesInteractor @Inject constructor(
    private val repository: DialoguesRepository
) {
    suspend fun getChatList(): ResultWrapper<List<DialogueRepoModel>> {
        val dialogues = repository.getChatList()
            if (dialogues is ResultWrapper.Success) {
                dialogues.value.map {
                    limitMaxUnreadMessageCount(it)
                }
        }
        return dialogues
    }

    private fun limitMaxUnreadMessageCount(dialogues: DialogueRepoModel) {
        if (dialogues.unreadMessageCount != null) {
            dialogues.unreadMessageCount = min(999999, dialogues.unreadMessageCount!!)
        }
    }
}