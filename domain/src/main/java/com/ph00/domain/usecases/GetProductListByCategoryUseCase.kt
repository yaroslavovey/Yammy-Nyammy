package com.ph00.domain.usecases

import com.ph00.domain.models.ProductModel
import com.ph00.domain.repositories.ProductsRepository
import kotlinx.coroutines.flow.Flow

class GetProductListByCategoryUseCase(private val productsRepository: ProductsRepository) {

    fun execute(category: String): Flow<List<ProductModel>> =
        productsRepository.getProductListByCategory(category)

}