package com.ph00.domain.usecases

import com.ph00.domain.models.UserModel
import com.ph00.domain.repositories.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class SetUserDataUseCase(
    private val userRepository: UserRepository,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) {

    suspend fun execute(data: UserModel): Boolean? =
        withContext(IO) {
            getCurrentUserUseCase.execute()?.let { user ->
                user.email?.let { userEmail ->
                    userRepository.setUserPersonalData(
                        UserModel(
                            name = data.name,
                            phoneNum = data.phoneNum,
                            email = userEmail
                        ), user.uid
                    )
                }
            }
        }

}