package com.ph00.domain.usecases

import com.ph00.domain.models.AddressModel
import com.ph00.domain.repositories.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class AddAddressUseCase(
    private val userRepository: UserRepository,
    private val getCurrentUserUidUseCase: GetCurrentUserUidUseCase
) {
    suspend fun execute(address: AddressModel): Boolean? =
        withContext(IO) {
            getCurrentUserUidUseCase.execute()?.let { userUid ->
                userRepository.addAddress(address, userUid)
            }
        }

}