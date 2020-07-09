package com.ph00.domain.usecases

import com.ph00.domain.models.UserModel
import com.ph00.domain.repositories.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class GetUserDataAsFlowUseCase(
    private val userRepository: UserRepository,
    private val getCurrentUserUidUseCase: GetCurrentUserUidUseCase
) {

    fun execute(): Flow<UserModel?>? =
            getCurrentUserUidUseCase.execute()?.let { userUid ->
                userRepository.getUserPersonalDataAsFlow(userUid)
            }

}