package com.ph00.domain.usecases

import com.google.firebase.firestore.ktx.toObject
import com.ph00.domain.models.AddressModel
import com.ph00.domain.repositories.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class GetAddressByUidUseCase(
    private val userRepository: UserRepository,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) {
    suspend fun execute(addressUid: String): AddressModel? =
        withContext(IO) {
            getCurrentUserUseCase.execute()?.uid?.let { userUid ->
                userRepository.getAddressByUid(addressUid, userUid)?.toObject<AddressModel>()
            }
        }

}