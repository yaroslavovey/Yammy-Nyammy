package com.phooper.yammynyammy.domain.usecases

import androidx.lifecycle.LiveData
import com.phooper.yammynyammy.data.models.ProductIdAndCount
import com.phooper.yammynyammy.domain.repositories.UserRepository

class GetAllCartProductsLiveDataUseCase(private val userRepository: UserRepository) {

    fun execute(): LiveData<List<ProductIdAndCount>> =
        userRepository.getAllCartProductsLiveData()
}