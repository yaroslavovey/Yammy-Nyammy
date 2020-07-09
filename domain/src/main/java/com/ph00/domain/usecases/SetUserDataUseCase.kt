package com.ph00.domain.usecases

import com.ph00.domain.models.UserModel
import com.ph00.domain.repositories.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class SetUserDataUseCase(
    private val userRepository: UserRepository,
    private val getCurrentUserUidUseCase: GetCurrentUserUidUseCase,
    private val getCurrentUserEmailUseCase: GetCurrentUserEmailUseCase
) {

    suspend fun execute(data: UserModel): Boolean? =
        withContext(IO) {
            getCurrentUserEmailUseCase.execute()?.let { email ->
                getCurrentUserUidUseCase.execute()?.let { uid ->
                    userRepository.setUserPersonalData(
                        UserModel(phoneNum = data.phoneNum, name = data.name, email = email), uid
                    )
                }
            }
        }

}