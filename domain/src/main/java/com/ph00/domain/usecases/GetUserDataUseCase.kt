package com.ph00.domain.usecases

import com.ph00.domain.models.UserModel
import com.ph00.domain.repositories.UserRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetUserDataUseCase@Inject constructor(
    private val userRepository: UserRepository,
    private val getCurrentUserUidUseCase: GetCurrentUserUidUseCase
) {

    fun execute(): Single<UserModel> =
        getCurrentUserUidUseCase.execute().flatMap { userUid ->
            userRepository.getUserPersonalData(userUid)
        }

}

