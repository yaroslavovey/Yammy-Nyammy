package com.phooper.yammynyammy.domain.usecases

import com.google.firebase.firestore.DocumentReference
import com.phooper.yammynyammy.domain.repositories.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class GetUserDataAsDocumentUseCase(
    private val userRepository: UserRepository,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) {

    suspend fun execute(): DocumentReference? =
        withContext(IO) {
            getCurrentUserUseCase.execute()?.uid?.let { userUid ->
                userRepository.getUserPersonalDataAsDocument(userUid)
            }
        }

}