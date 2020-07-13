package com.ph00.domain.usecases

import com.ph00.domain.models.ProductModel
import com.ph00.domain.repositories.ProductsRepository
import kotlinx.coroutines.flow.Flow

class GetProductByIdUseCase(private val productsRepository: ProductsRepository) {

    fun execute(id: Int): Flow<ProductModel> =
        productsRepository.getProductById(id)

}