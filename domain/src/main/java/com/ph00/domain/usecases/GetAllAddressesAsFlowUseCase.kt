package com.ph00.domain.usecases

import com.ph00.domain.models.AddressModel
import com.ph00.domain.repositories.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class GetAllAddressesAsFlowUseCase(
    private val userRepository: UserRepository,
    private val getCurrentUserUidUseCase: GetCurrentUserUidUseCase
) {
    fun execute(): Flow<List<AddressModel>?>? =
            getCurrentUserUidUseCase.execute()?.let { userUid ->
                userRepository.getAllAddressesAsFlow(userUid)
        }

}