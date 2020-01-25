package com.tla.uchattut.data.repositories.dialogues

import com.tla.uchattut.data.network.RestApi
import com.tla.uchattut.data.network.ResultWrapper
import com.tla.uchattut.data.network.safeApiCall
import com.tla.uchattut.data.repositories.dialogues.models.DialogueRepoModel
import com.tla.uchattut.domain.dialogues.DialoguesRepository
import javax.inject.Inject

class DialoguesRepositoryImpl @Inject constructor(
    private val networkApi: RestApi
) : DialoguesRepository {

    override suspend fun getChatList(): ResultWrapper<List<DialogueRepoModel>> {
        return safeApiCall {
            networkApi.getChats()
        }
    }
}