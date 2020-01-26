package com.tla.uchattut.presentation.conversation.search_user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tla.uchattut.data.network.ResultWrapper
import com.tla.uchattut.data.network.logException
import com.tla.uchattut.data.network.model.MinimalUserNetworkModel
import com.tla.uchattut.domain.search.SearchInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchUserViewModel @Inject constructor(
    private val searchInteractor: SearchInteractor
) : ViewModel() {

    private val usersLiveData = MutableLiveData<List<MinimalUserNetworkModel>>()

    fun searchUsers(query: String) = viewModelScope.launch(Dispatchers.IO) {
        when (val usersResult = searchInteractor.fetchUsers(query)) {
            is ResultWrapper.Success -> {
                usersLiveData.postValue(usersResult.value)
            }
            is ResultWrapper.Error -> {
                logException(this, usersResult.throwable)
            }
        }
    }

    fun getUsersLiveData(): LiveData<List<MinimalUserNetworkModel>> {
        return usersLiveData
    }
}