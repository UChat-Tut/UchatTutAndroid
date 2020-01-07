package com.tla.uchattut.presentation.dialogues.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.tla.uchattut.data.repositories.dialogues.DialoguesRepositoryImpl
import com.tla.uchattut.data.repositories.dialogues.FakeDialoguesRepository
import com.tla.uchattut.domain.dialogues.DialoguesInteractor
import kotlinx.coroutines.Dispatchers

class DialoguesViewModel : ViewModel() {
    private val chatListInteractor = DialoguesInteractor(FakeDialoguesRepository())

    val state: MutableLiveData<State> = MutableLiveData(State.CONTENT)

    val chatList = liveData(Dispatchers.IO) {
        state.postValue(State.LOADING)

        val chatList = chatListInteractor.getChatList()
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