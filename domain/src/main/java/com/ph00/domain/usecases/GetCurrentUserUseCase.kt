package com.ph00.domain.usecases

import com.google.firebase.auth.FirebaseUser
import com.ph00.domain.repositories.AuthRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class GetCurrentUserUseCase(private val authRepository: AuthRepository) {

    suspend fun execute(): FirebaseUser? =
        withContext(IO) { authRepository.getCurrentUser() }

}