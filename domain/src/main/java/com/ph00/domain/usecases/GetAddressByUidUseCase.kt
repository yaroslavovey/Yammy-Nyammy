package com.ph00.domain.usecases

import com.ph00.domain.models.AddressModel
import com.ph00.domain.repositories.UserRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetAddressByUidUseCase@Inject constructor(
    private val userRepository: UserRepository,
    private val getCurrentUserUidUseCase: GetCurrentUserUidUseCase
) {

    fun execute(addressUid: String): Single<AddressModel> =
        getCurrentUserUidUseCase.execute().flatMap { userUid ->
            userRepository.getAddressByUid(addressUid, userUid)
        }

}