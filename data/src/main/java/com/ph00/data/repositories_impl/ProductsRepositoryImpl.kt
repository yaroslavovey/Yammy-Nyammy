package com.ph00.data.repositories_impl

import com.ph00.data.api.ShopApi
import com.ph00.data.toModel
import com.ph00.domain.models.ProductModel
import com.ph00.domain.repositories.ProductsRepository

class ProductsRepositoryImpl(private val shopApi: ShopApi) : ProductsRepository {

    override suspend fun getProductListByCategory(category: String): List<ProductModel>? {
        return try {
            shopApi.getProductListByCategory(category).map { it.toModel() }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getProductById(id: Int): ProductModel? {
        return try {
            shopApi.getProductById(id).toModel()
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getProductListByIds(ids: List<Int>): List<ProductModel>? {
        return try {
            shopApi.getProductListByIds(ids).map { it.toModel() }
        } catch (e: Exception) {
            null
        }
    }
}