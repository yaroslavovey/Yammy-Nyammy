package com.ph00.domain.usecases

import com.ph00.domain.models.AddressModel
import com.ph00.domain.repositories.UserRepository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class AddAddressUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val getCurrentUserUidUseCase: GetCurrentUserUidUseCase
) {

    fun execute(address: AddressModel): Completable =
        getCurrentUserUidUseCase.execute().flatMapCompletable { userUid ->
            userRepository.addAddress(address, userUid)
        }

}