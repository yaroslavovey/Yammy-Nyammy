package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.toObject
import com.phooper.yammynyammy.data.models.User
import com.phooper.yammynyammy.data.repositories.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MakeOrderViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _state = MutableLiveData<ViewState>()
    val state: LiveData<ViewState> get() = _state

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> get() = _username

    private val _phoneNum = MutableLiveData<String>()
    val phoneNum: LiveData<String> get() = _phoneNum

    init {
        viewModelScope.launch {
            loadUserData()
        }
    }

    private suspend fun loadUserData() {
        withContext(IO) {
            _state.postValue(ViewState.LOADING)
            userRepository.getCurrentUserData()?.toObject<User>()?.let {
                _phoneNum.postValue(it.phoneNum.toString())
                _username.postValue(it.name)
            }
            _state.postValue(ViewState.DEFAULT)
        }
    }

    enum class ViewState {
        DEFAULT,
        LOADING,
    }
}