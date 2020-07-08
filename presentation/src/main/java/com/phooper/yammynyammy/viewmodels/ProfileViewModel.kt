package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.toObject
import com.ph00.domain.models.UserModel
import com.ph00.domain.usecases.GetUserDataAsDocumentUseCase
import com.ph00.domain.usecases.SignOutUseCase
import com.phooper.yammynyammy.entities.User
import com.phooper.yammynyammy.utils.Event
import com.phooper.yammynyammy.utils.toPresentation
import kotlinx.coroutines.launch
import timber.log.Timber

class ProfileViewModel(
    private val getUserDataAsDocumentUseCase: GetUserDataAsDocumentUseCase,
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {

    private val _state = MutableLiveData<ViewState>(ViewState.LOADING)
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
        getUserDataAsDocumentUseCase.execute()?.let {
            it.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    _event.postValue(Event(ViewEvent.ERROR))
                    return@addSnapshotListener
                }
                documentSnapshot?.toObject<UserModel>()?.toPresentation()?.let { user ->
                    _userData.postValue(user)
                    _state.postValue(ViewState.DEFAULT)
                }
            }
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