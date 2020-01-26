package com.tla.uchattut.data.repositories.search

import com.tla.uchattut.data.network.RestApi
import com.tla.uchattut.data.network.ResultWrapper
import com.tla.uchattut.data.network.model.MinimalUserNetworkModel
import com.tla.uchattut.data.network.safeApiCall
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val networkApi: RestApi
) {

    suspend fun fetchUsers(query: String): ResultWrapper<List<MinimalUserNetworkModel>> {
        return safeApiCall { networkApi.fetchUsers(query) }
    }
}