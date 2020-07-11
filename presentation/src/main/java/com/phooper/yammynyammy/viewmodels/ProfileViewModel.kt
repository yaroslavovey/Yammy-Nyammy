package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ph00.domain.usecases.GetCurrentUserEmailUseCase
import com.ph00.domain.usecases.SignOutUseCase
import com.phooper.yammynyammy.utils.Event
import kotlinx.coroutines.launch

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
        getCurrentUserEmailUseCase.execute()?.let {
            _email.value = it
        }
        _state.postValue(ViewState.DEFAULT)
    }

    fun signOut() = viewModelScope.launch {
        signOutUseCase.execute()
    }

    enum class ViewState {
        LOADING,
        DEFAULT
    }

    enum class ViewEvent {
        ERROR
    }
}