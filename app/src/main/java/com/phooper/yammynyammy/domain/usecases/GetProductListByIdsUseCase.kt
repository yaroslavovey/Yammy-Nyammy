package com.phooper.yammynyammy.domain.usecases

import com.phooper.yammynyammy.domain.models.Product
import com.phooper.yammynyammy.domain.repositories.ProductsRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class GetProductListByIdsUseCase(private val productsRepository: ProductsRepository) {

    suspend fun execute(ids: List<Int>): List<Product>? =
        withContext(IO) { productsRepository.getProductListByIds(ids) }

}