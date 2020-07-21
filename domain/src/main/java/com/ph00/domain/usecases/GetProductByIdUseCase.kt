package com.ph00.domain.usecases

import com.ph00.domain.models.ProductModel
import com.ph00.domain.repositories.ProductsRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetProductByIdUseCase@Inject constructor(private val productsRepository: ProductsRepository) {

    fun execute(id: Int): Single<ProductModel> =
        productsRepository.getProductById(id)

}