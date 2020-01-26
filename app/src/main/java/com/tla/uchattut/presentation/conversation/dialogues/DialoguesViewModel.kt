package com.tla.uchattut.presentation.conversation.dialogues

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.tla.uchattut.data.network.ResultWrapper
import com.tla.uchattut.di.dialogues.DialoguesComponent
import com.tla.uchattut.domain.dialogues.DialoguesInteractor
import com.tla.uchattut.domain.search.SearchInteractor
import com.tla.uchattut.presentation._common.ScopeViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class DialoguesViewModel @Inject constructor(
    private val dialoguesInteractor: DialoguesInteractor,
    private val seachInteractor: SearchInteractor
) : ScopeViewModel(DialoguesComponent::class) {

    val state: MutableLiveData<State> = MutableLiveData(
        State.CONTENT
    )

    val chatList = liveData(Dispatchers.IO) {
        state.postValue(State.LOADING)

        val chatList = dialoguesInteractor.getChatList()
        when (chatList) {
            is ResultWrapper.Success -> {
                emit(chatList.value)

                if (chatList.value.isNullOrEmpty()) {
                    state.postValue(State.EMPTY)
                } else {
                    state.postValue(State.CONTENT)
                }
            }
            is ResultWrapper.Error -> {
                state.postValue(State.EMPTY)
            }
        }
    }

    enum class State {
        LOADING, CONTENT, EMPTY
    }
}