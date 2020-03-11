package com.tla.uchattut.presentation.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tla.uchattut.data.network.model.UserNetworkModel
import com.tla.uchattut.di.search_contacted_user.SearchContactedUserComponent
import com.tla.uchattut.domain.search_contacted_users.SearchContactedUsersInteractor
import com.tla.uchattut.presentation._common.ScopeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchContactedUserViewModel(
    private val searchInteractor: SearchContactedUsersInteractor
) : ScopeViewModel(SearchContactedUserComponent::class) {

    private val contactedUsersLiveData = MutableLiveData<List<UserNetworkModel>>()

    fun getContactedUsersLiveData(): LiveData<List<UserNetworkModel>> {
        return contactedUsersLiveData
    }

    fun requestContactedUsers(query: String = "") = viewModelScope.launch(Dispatchers.IO) {
        val users = searchInteractor.fetchContactedUsers(query)
        contactedUsersLiveData.postValue(users)
    }
}