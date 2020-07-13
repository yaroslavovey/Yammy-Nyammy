package com.ph00.domain.usecases

import com.ph00.domain.models.UserModel
import com.ph00.domain.repositories.UserRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat

@FlowPreview
class GetUserDataUseCase(
    private val userRepository: UserRepository,
    private val getCurrentUserUidUseCase: GetCurrentUserUidUseCase
) {

    fun execute(): Flow<UserModel> =
        getCurrentUserUidUseCase.execute().flatMapConcat { userUid ->
            userRepository.getUserPersonalData(userUid)
        }

}

