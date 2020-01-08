package com.tla.uchattut.presentation.dialogues.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.tla.uchattut.data.repositories.dialogues.FakeDialoguesRepository
import com.tla.uchattut.di.dialogues.DialoguesComponent
import com.tla.uchattut.domain.dialogues.DialoguesInteractor
import com.tla.uchattut.presentation._common.ScopeViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class DialoguesViewModel @Inject constructor(
    private val dialoguesInteractor: DialoguesInteractor
): ScopeViewModel(DialoguesComponent::class) {

    val state: MutableLiveData<State> = MutableLiveData(State.CONTENT)

    val chatList = liveData(Dispatchers.IO) {
        state.postValue(State.LOADING)

        val chatList = dialoguesInteractor.getChatList()
        emit(chatList)

        if (chatList.isNullOrEmpty()) {
            state.postValue(State.EMPTY)
        } else {
            state.postValue(State.CONTENT)
        }
    }

    enum class State {
        LOADING, CONTENT, EMPTY
    }
}