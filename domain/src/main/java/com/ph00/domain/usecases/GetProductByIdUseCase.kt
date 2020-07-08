package com.ph00.domain.usecases

import com.ph00.domain.models.ProductModel
import com.ph00.domain.repositories.ProductsRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class GetProductByIdUseCase(private val productsRepository: ProductsRepository) {

    suspend fun execute(id: Int): ProductModel? =
        withContext(IO) { productsRepository.getProductById(id) }

}