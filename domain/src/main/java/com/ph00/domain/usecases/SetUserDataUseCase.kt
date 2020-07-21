package com.ph00.domain.usecases

import com.ph00.domain.models.UserModel
import com.ph00.domain.repositories.UserRepository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class SetUserDataUseCase@Inject constructor(
    private val userRepository: UserRepository,
    private val getCurrentUserUidUseCase: GetCurrentUserUidUseCase
) {

    fun execute(data: UserModel): Completable =
        getCurrentUserUidUseCase.execute().flatMapCompletable { uid ->
            userRepository.setUserPersonalData(
                UserModel(phoneNum = data.phoneNum, name = data.name), uid
            )
        }

}

