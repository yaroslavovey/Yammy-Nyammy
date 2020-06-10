package com.phooper.yammynyammy.data.repositories

import com.phooper.yammynyammy.data.api.ShopApi
import com.phooper.yammynyammy.utils.Constants.Companion.CHOCOLATE_CATEGORY
import com.phooper.yammynyammy.utils.Constants.Companion.COOKIES_CATEGORY
import com.phooper.yammynyammy.utils.Constants.Companion.CUPCAKES_CATEGORY
import com.phooper.yammynyammy.utils.Constants.Companion.ICE_CREAM_CATEGORY

class ProductsRepository(private val shopApi: ShopApi) {

    suspend fun getProductListByCategory(category : String) =
        shopApi.getProductListByCategory(category)

}