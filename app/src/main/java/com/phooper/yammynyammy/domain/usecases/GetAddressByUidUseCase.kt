package com.phooper.yammynyammy.domain.usecases

import com.google.firebase.firestore.ktx.toObject
import com.phooper.yammynyammy.domain.models.Address
import com.phooper.yammynyammy.domain.repositories.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class GetAddressByUidUseCase(
    private val userRepository: UserRepository,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) {
    suspend fun execute(addressUid: String): Address? =
        withContext(IO) {
            getCurrentUserUseCase.execute()?.uid?.let { userUid ->
                userRepository.getAddressByUid(addressUid, userUid)?.toObject<Address>()
            }
        }

}