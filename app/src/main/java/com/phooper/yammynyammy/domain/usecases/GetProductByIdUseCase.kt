package com.phooper.yammynyammy.domain.usecases

import com.phooper.yammynyammy.data.models.Product
import com.phooper.yammynyammy.domain.repositories.ProductsRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class GetProductByIdUseCase(private val productsRepository: ProductsRepository) {

    suspend fun execute(id: Int): Product? =
        withContext(IO) { productsRepository.getProductById(id) }

}