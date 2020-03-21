package com.tla.uchattut.data.repositories.search_contacted_users

import com.tla.uchattut.data.network.model.UserNetworkModel
import javax.inject.Inject

class SearchContactedUsersRepositoryImpl @Inject constructor() {

    suspend fun fetchContactedUsers(query: String): List<UserNetworkModel> {
        return mockedUsers.filter {
           it.name.contains(query, ignoreCase = true)
        }
    }

    companion object {
        private const val mutualUrl = "https://i.pinimg.com/originals/a4/4a/f3/a44af3bb5f074e3cdb4be8a56232c996.jpg"
        private val mockedUsers = listOf(
            UserNetworkModel(1, "Алексей Алексеевич Алексеев", "alexeev@gmail.com", mutualUrl),
            UserNetworkModel(2, "Олег Самсонов", "samsonov@ya.ru", mutualUrl),
            UserNetworkModel(3, "Алёна Арсеньева", "arsenieva@mail.ru", mutualUrl)
        )
    }
}