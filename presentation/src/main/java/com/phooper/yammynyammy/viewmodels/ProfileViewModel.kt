package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.*
import com.ph00.domain.usecases.GetUserDataAsFlowUseCase
import com.ph00.domain.usecases.SignOutUseCase
import com.phooper.yammynyammy.entities.User
import com.phooper.yammynyammy.utils.Event
import com.phooper.yammynyammy.utils.toPresentation
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getUserDataAsFlowUseCase: GetUserDataAsFlowUseCase,
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {

    private val _state = MutableLiveData<ViewState>(ViewState.LOADING)
    val state: LiveData<ViewState> get() = _state

    private val _event = MutableLiveData<Event<ViewEvent>>()
    val event: LiveData<Event<ViewEvent>> get() = _event

    val userData: LiveData<User>?
        get() = getUserDataAsFlowUseCase.execute()?.asLiveData(IO)?.switchMap { userData ->
            liveData(viewModelScope.coroutineContext) {
                if (userData != null) emit(userData.toPresentation())
                _state.postValue(ViewState.DEFAULT)
            }
        }

    fun signOut() = viewModelScope.launch {
        signOutUseCase.execute()
        _event.postValue(Event(ViewEvent.NAVIGATE_TO_LOGIN_ACTIVITY))
    }

    enum class ViewState {
        LOADING,
        DEFAULT
    }

    enum class ViewEvent {
        ERROR,
        NAVIGATE_TO_LOGIN_ACTIVITY
    }
}