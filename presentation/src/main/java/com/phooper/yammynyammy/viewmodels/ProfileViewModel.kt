package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ph00.domain.usecases.GetCurrentUserEmailUseCase
import com.ph00.domain.usecases.SignOutUseCase
import com.phooper.yammynyammy.utils.Event
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class ProfileViewModel(
    private val getCurrentUserEmailUseCase: GetCurrentUserEmailUseCase,
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {

    private val _state = MutableLiveData<ViewState>(ViewState.LOADING)
    val state: LiveData<ViewState> get() = _state

    private val _event = MutableLiveData<Event<ViewEvent>>()
    val event: LiveData<Event<ViewEvent>> get() = _event

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> get() = _email

    init {
        loadUserEmail()
    }

    private fun loadUserEmail() {
        getCurrentUserEmailUseCase
            .execute()
            .onStart { _state.value = ViewState.LOADING }
            .onEach { _email.value = it }
            .onCompletion { _state.value = ViewState.DEFAULT }
            .catch { _event.value = Event(ViewEvent.ERROR) }
            .launchIn(viewModelScope)
    }

    fun signOut() {
        signOutUseCase
            .execute()
            .onStart { _state.value = ViewState.LOADING }
            .onCompletion { _state.value = ViewState.DEFAULT }
            .catch { _event.value = Event(ViewEvent.ERROR) }
            .launchIn(viewModelScope)
    }

    enum class ViewState {
        LOADING,
        DEFAULT
    }

    enum class ViewEvent {
        ERROR
    }
}