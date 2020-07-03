package com.phooper.yammynyammy.domain.usecases

import com.google.firebase.firestore.CollectionReference
import com.phooper.yammynyammy.domain.repositories.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class GetAddressesAsCollectionUseCase(
    private val userRepository: UserRepository,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) {

    suspend fun execute(): CollectionReference? =
        withContext(IO) {
            getCurrentUserUseCase.execute()?.uid?.let { userUid ->
                userRepository.getAddressesAsCollection(userUid)
            }
        }

}