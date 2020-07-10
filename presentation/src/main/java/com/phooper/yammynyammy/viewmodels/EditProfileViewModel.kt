package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ph00.domain.models.UserModel
import com.ph00.domain.usecases.GetUserDataUseCase
import com.ph00.domain.usecases.SetUserDataUseCase
import com.phooper.yammynyammy.entities.User
import com.phooper.yammynyammy.utils.Event
import com.phooper.yammynyammy.utils.toPresentation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

open class EditProfileViewModel(
    private val getUserDataUseCase: GetUserDataUseCase,
    private val setUserDataUseCase: SetUserDataUseCase
) : ViewModel() {

    private val _state = MutableLiveData<ViewState>()
    val state: LiveData<ViewState> get() = _state

    private val _event = MutableLiveData<Event<ViewEvent>>()
    val event: LiveData<Event<ViewEvent>> get() = _event

    private val _userData = MutableLiveData<User>()
    val userData: LiveData<User> get() = _userData

    init {
        viewModelScope.launch {
            loadUser()
        }
    }

    private suspend fun loadUser() {
        getUserDataUseCase.execute()?.toPresentation()?.let {
            _userData.postValue(it)
            return
        }
        delay(5000)
        loadUser()
    }

    fun saveUser(name: String, phone: String) {
        _state.value = ViewState.LOADING
        viewModelScope.launch {
            setUserDataUseCase.execute(
                UserModel(
                    name = name,
                    phoneNum = phone
                )
            )?.let {
                _event.postValue(Event(ViewEvent.SUCCESS))
                _state.value = ViewState.DEFAULT
                return@launch
            }
            //TODO Never reached :(
            //Need to set firestore request timeout
            _event.postValue(Event(ViewEvent.FAILURE))
            _state.value = ViewState.DEFAULT
        }
    }

    enum class ViewState {
        DEFAULT,
        LOADING,
    }

    enum class ViewEvent {
        FAILURE,
        SUCCESS
    }
}