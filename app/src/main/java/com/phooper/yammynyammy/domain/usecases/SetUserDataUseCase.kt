package com.phooper.yammynyammy.domain.usecases

import com.phooper.yammynyammy.data.models.User
import com.phooper.yammynyammy.domain.repositories.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class SetUserDataUseCase(
    private val userRepository: UserRepository,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) {

    suspend fun execute(data: User): Boolean? =
        withContext(IO) {
            getCurrentUserUseCase.execute()?.uid?.let { userUid ->
                userRepository.setUserPersonalData(data, userUid)
            }
        }

}