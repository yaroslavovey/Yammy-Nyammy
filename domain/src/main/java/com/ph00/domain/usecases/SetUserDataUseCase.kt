package com.ph00.domain.usecases

import com.ph00.domain.models.UserModel
import com.ph00.domain.repositories.UserRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat

@FlowPreview
class SetUserDataUseCase(
    private val userRepository: UserRepository,
    private val getCurrentUserUidUseCase: GetCurrentUserUidUseCase
) {

    fun execute(data: UserModel): Flow<Unit> =
        getCurrentUserUidUseCase.execute().flatMapConcat { uid ->
            userRepository.setUserPersonalData(
                UserModel(phoneNum = data.phoneNum, name = data.name), uid
            )
        }
}

