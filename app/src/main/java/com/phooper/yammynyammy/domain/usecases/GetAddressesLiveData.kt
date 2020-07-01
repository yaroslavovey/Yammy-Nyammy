package com.phooper.yammynyammy.domain.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject
import com.livermor.delegateadapter.delegate.diff.DiffUtilItem
import com.phooper.yammynyammy.data.models.Address
import com.phooper.yammynyammy.domain.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GetAddressesLiveData(
    private val userRepository: UserRepository,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) {

    private val _addressesListLiveData = MutableLiveData<List<DiffUtilItem>>()

    fun execute(): LiveData<List<DiffUtilItem>> {
        return _addressesListLiveData
    }

    //TODO Refactor :(
    init {
        CoroutineScope(IO).launch { initLiveData() }
    }

    private suspend fun initLiveData() {
        withContext(IO) {
            getCurrentUserUseCase.execute()?.uid?.let { userUid ->
                userRepository
                    .getAddressesAsCollection(userUid)
                    ?.addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
                        if (e != null) {
                            return@EventListener
                        }
                        _addressesListLiveData.postValue(value?.documents?.mapNotNull { it.toObject<Address>() })
                    })
            }
        }
    }
}