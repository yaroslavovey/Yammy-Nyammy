package com.ph00.domain.repositories

import com.ph00.domain.models.ProductModel

interface ProductsRepository {
    suspend fun getProductListByCategory(category: String): List<ProductModel>?
    suspend fun getProductById(id: Int): ProductModel?
    suspend fun getProductListByIds(ids: List<Int>): List<ProductModel>?
}