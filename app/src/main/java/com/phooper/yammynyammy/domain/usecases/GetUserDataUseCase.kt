package com.phooper.yammynyammy.domain.usecases

import com.google.firebase.firestore.ktx.toObject
import com.phooper.yammynyammy.domain.models.User
import com.phooper.yammynyammy.domain.repositories.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class GetUserDataUseCase(
    private val userRepository: UserRepository,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) {

    suspend fun execute(): User? =
        withContext(IO) {
            getCurrentUserUseCase.execute()?.uid?.let { userUid ->
                userRepository.getUserPersonalData(userUid)?.toObject<User>()
            }
        }

}