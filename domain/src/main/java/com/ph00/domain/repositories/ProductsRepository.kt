package com.ph00.domain.repositories

import com.ph00.domain.models.ProductModel
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    fun getProductListByCategory(category: String): Flow<List<ProductModel>>
    fun getProductById(id: Int): Flow<ProductModel>
    fun getProductListByIds(ids: List<Int>): Flow<List<ProductModel>>
}