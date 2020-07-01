package com.phooper.yammynyammy.domain.repositories

import com.phooper.yammynyammy.data.models.Product

interface ProductsRepository {
    suspend fun getProductListByCategory(category: String): List<Product>?
    suspend fun getProductById(id: Int): Product?
    suspend fun getProductListByIds(ids: List<Int>): List<Product>?
}