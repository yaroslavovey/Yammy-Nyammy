package com.ph00.data.api

import com.ph00.data.entities.ProductEntity
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ShopApi {

    @GET("ph00per/Fake-json-server-for-Yammy-Nyammy/goods")
    suspend fun getProductListByCategory(@Query("category") category: String): List<ProductEntity>

    @GET("ph00per/Fake-json-server-for-Yammy-Nyammy/goods/{id}")
    suspend fun getProductById(@Path("id") id: Int): ProductEntity

    @GET("ph00per/Fake-json-server-for-Yammy-Nyammy/goods/")
    suspend fun getProductListByIds(@Query("id") ids: List<Int>): List<ProductEntity>

}
