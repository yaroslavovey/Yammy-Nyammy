package com.phooper.yammynyammy.domain.usecases

import com.google.firebase.auth.FirebaseUser
import com.phooper.yammynyammy.domain.repositories.AuthRepository
import com.phooper.yammynyammy.domain.repositories.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class GetCurrentUserUseCase(private val authRepository: AuthRepository) {

    suspend fun execute(): FirebaseUser? =
        withContext(IO) { authRepository.getCurrentUser() }

}