package com.ph00.domain.usecases

import com.ph00.domain.models.UserModel
import com.ph00.domain.repositories.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class SetUserDataUseCase(
    private val userRepository: UserRepository,
    private val getCurrentUserUidUseCase: GetCurrentUserUidUseCase
) {

    suspend fun execute(data: UserModel): Boolean? =
        withContext(IO) {
            getCurrentUserUidUseCase.execute()?.let { uid ->
                userRepository.setUserPersonalData(
                    UserModel(phoneNum = data.phoneNum, name = data.name), uid
                )
            }
        }

}