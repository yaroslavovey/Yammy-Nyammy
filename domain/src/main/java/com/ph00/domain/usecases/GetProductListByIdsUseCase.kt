package com.ph00.domain.usecases

import com.ph00.domain.models.ProductModel
import com.ph00.domain.repositories.ProductsRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class GetProductListByIdsUseCase(private val productsRepository: ProductsRepository) {

    fun execute(ids: List<Int>): Flow<List<ProductModel>> =
        productsRepository.getProductListByIds(ids)

}