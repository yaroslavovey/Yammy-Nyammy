package com.phooper.yammynyammy.data.repositories

import com.phooper.yammynyammy.data.api.ShopApi
import com.phooper.yammynyammy.data.models.Product

class ProductsRepository(private val shopApi: ShopApi) {

    suspend fun getProductListByCategory(category: String): List<Product>? {
        return try {
            shopApi.getProductListByCategory(category)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getProductById(id: Int): Product? {
        return try {
            shopApi.getProductById(id)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getProductListByIds(ids: List<Int>): List<Product>? {
        return try {
            shopApi.getProductListByIds(ids)
        } catch (e: Exception) {
            null
        }
    }
}