package com.tla.uchattut.data.repositories.search

import com.tla.uchattut.data.network.RestApi
import com.tla.uchattut.data.network.RetrofitClient
import com.tla.uchattut.data.network.model.MinimalUserNetworkModel
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor() {
    private val networkApi: RestApi = RetrofitClient.networkApi

    suspend fun fetchUsers(query: String): List<MinimalUserNetworkModel> {
        return networkApi.fetchUsers(query)
    }

}