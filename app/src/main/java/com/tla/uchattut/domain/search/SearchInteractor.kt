package com.tla.uchattut.domain.search

import com.tla.uchattut.data.network.ResultWrapper
import com.tla.uchattut.data.network.model.MinimalUserNetworkModel
import com.tla.uchattut.data.repositories.search.SearchRepositoryImpl
import javax.inject.Inject

class SearchInteractor @Inject constructor(
    private val repository: SearchRepositoryImpl
) {
    suspend fun fetchUsers(query: String): ResultWrapper<List<MinimalUserNetworkModel>> {
        return repository.fetchUsers(query)
    }
}