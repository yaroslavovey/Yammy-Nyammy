package com.ph00.domain.usecases

import com.ph00.domain.models.AddressModel
import com.ph00.domain.repositories.UserRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat

@FlowPreview
class GetAllAddressesUseCase(
    private val userRepository: UserRepository,
    private val getCurrentUserUidUseCase: GetCurrentUserUidUseCase
) {

    fun execute(): Flow<List<AddressModel>> =
        getCurrentUserUidUseCase.execute().flatMapConcat { userUid ->
            userRepository.getAllAddresses(userUid)
        }

}