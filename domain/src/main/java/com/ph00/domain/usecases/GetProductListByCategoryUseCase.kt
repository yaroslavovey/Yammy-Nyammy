package com.ph00.domain.usecases

import com.ph00.domain.models.ProductModel
import com.ph00.domain.repositories.ProductsRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class GetProductListByCategoryUseCase(private val productsRepository: ProductsRepository) {

    suspend fun execute(category: String): List<ProductModel>? =
        withContext(IO) { productsRepository.getProductListByCategory(category) }

}