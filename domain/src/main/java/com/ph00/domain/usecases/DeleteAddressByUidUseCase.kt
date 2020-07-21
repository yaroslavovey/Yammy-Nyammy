package com.ph00.domain.usecases

import com.ph00.domain.repositories.UserRepository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class DeleteAddressByUidUseCase@Inject constructor(
    private val userRepository: UserRepository,
    private val getCurrentUserUidUseCase: GetCurrentUserUidUseCase
) {

    fun execute(addressUid: String): Completable =
        getCurrentUserUidUseCase.execute().flatMapCompletable { userUid ->
            userRepository.deleteAddressByUid(addressUid, userUid)
        }

}