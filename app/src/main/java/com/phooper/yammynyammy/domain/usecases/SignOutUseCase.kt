package com.phooper.yammynyammy.domain.usecases

import com.phooper.yammynyammy.domain.repositories.AuthRepository
import com.phooper.yammynyammy.domain.repositories.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class SignOutUseCase(private val authRepository: AuthRepository) {

    suspend fun execute() =
        withContext(IO) { authRepository.signOut() }

}