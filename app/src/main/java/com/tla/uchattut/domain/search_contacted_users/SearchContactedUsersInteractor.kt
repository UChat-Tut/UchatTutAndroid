package com.tla.uchattut.domain.search_contacted_users

import com.tla.uchattut.data.network.model.UserNetworkModel
import com.tla.uchattut.data.repositories.search_contacted_users.SearchContactedUsersRepositoryImpl
import javax.inject.Inject

class SearchContactedUsersInteractor @Inject constructor(
    private val repository: SearchContactedUsersRepositoryImpl
) {
    suspend fun fetchContactedUsers(query: String): List<UserNetworkModel> {
        return repository.fetchContactedUsers(query)
    }
}