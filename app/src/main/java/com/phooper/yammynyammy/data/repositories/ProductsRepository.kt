package com.phooper.yammynyammy.data.repositories

import com.phooper.yammynyammy.data.api.ShopApi

class ProductsRepository(private val shopApi: ShopApi) {

    suspend fun getAllIceCream() = shopApi.getProductListByCategory(category =  "1")


}