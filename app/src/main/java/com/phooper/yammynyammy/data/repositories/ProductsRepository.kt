package com.phooper.yammynyammy.data.repositories

import com.phooper.yammynyammy.data.api.ShopApi

class ProductsRepository(private val shopApi: ShopApi) {

    suspend fun getProductListByCategory(category: String) =
        shopApi.getProductListByCategory(category)

    suspend fun getProductById(id: Int) = shopApi.getProductById(id)

    suspend fun getProductListByIds(ids: List<Int>) = shopApi.getProductListByIds(ids)
}