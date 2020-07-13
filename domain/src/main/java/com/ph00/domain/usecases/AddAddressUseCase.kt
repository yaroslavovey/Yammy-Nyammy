package com.ph00.domain.usecases

import com.ph00.domain.models.AddressModel
import com.ph00.domain.repositories.UserRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat

@FlowPreview
class AddAddressUseCase(
    private val userRepository: UserRepository,
    private val getCurrentUserUidUseCase: GetCurrentUserUidUseCase
) {

    fun execute(address: AddressModel): Flow<Unit> =
        getCurrentUserUidUseCase.execute().flatMapConcat { userUid ->
            userRepository.addAddress(address, userUid)
        }

}