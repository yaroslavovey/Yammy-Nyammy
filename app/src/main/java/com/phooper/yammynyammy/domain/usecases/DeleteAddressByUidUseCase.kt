package com.phooper.yammynyammy.domain.usecases

import com.phooper.yammynyammy.domain.repositories.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class DeleteAddressByUidUseCase(
    private val userRepository: UserRepository,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) {
    suspend fun execute(addressUid: String): Boolean? =
        withContext(IO) {
            getCurrentUserUseCase.execute()?.uid?.let { userUid ->
                userRepository.deleteAddressByUid(addressUid, userUid)
            }
        }

}