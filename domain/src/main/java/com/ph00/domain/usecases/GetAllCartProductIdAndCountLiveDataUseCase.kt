package com.ph00.domain.usecases

import androidx.lifecycle.LiveData
import com.ph00.domain.models.ProductIdAndCountModel
import com.ph00.domain.repositories.UserRepository

class GetAllCartProductIdAndCountLiveDataUseCase(private val userRepository: UserRepository) {

    fun execute(): LiveData<List<ProductIdAndCountModel>> =
        userRepository.getAllCartProductsLiveData()

}