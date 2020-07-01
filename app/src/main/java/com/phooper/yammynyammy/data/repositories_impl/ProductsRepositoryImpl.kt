package com.phooper.yammynyammy.data.repositories_impl

import com.phooper.yammynyammy.data.api.ShopApi
import com.phooper.yammynyammy.data.models.Product
import com.phooper.yammynyammy.domain.repositories.ProductsRepository

class ProductsRepositoryImpl(private val shopApi: ShopApi) : ProductsRepository {

    override suspend fun getProductListByCategory(category: String): List<Product>? {
        return try {
            shopApi.getProductListByCategory(category)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getProductById(id: Int): Product? {
        return try {
            shopApi.getProductById(id)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getProductListByIds(ids: List<Int>): List<Product>? {
        return try {
            shopApi.getProductListByIds(ids)
        } catch (e: Exception) {
            null
        }
    }
}