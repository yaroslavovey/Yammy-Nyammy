package com.phooper.yammynyammy.domain.usecases

import com.phooper.yammynyammy.domain.models.User
import com.phooper.yammynyammy.domain.repositories.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class SetUserDataUseCase(
    private val userRepository: UserRepository,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) {

    suspend fun execute(data: User): Boolean? =
        withContext(IO) {
            getCurrentUserUseCase.execute()?.let { user ->
                user.email?.let { userEmail ->
                    userRepository.setUserPersonalData(
                        User(
                            name = data.name,
                            phoneNum = data.phoneNum,
                            email = userEmail
                        ), user.uid
                    )
                }
            }
        }

}