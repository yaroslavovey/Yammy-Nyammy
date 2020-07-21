package com.ph00.data.api

import com.ph00.data.entities.ProductEntity
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ShopApi {

    @GET("ph00per/Fake-json-server-for-Yammy-Nyammy/goods")
    fun getProductListByCategory(@Query("category") category: Int): Single<List<ProductEntity>>

    @GET("ph00per/Fake-json-server-for-Yammy-Nyammy/goods/{id}")
    fun getProductById(@Path("id") id: Int): Single<ProductEntity>

    @GET("ph00per/Fake-json-server-for-Yammy-Nyammy/goods/")
    fun getProductListByIds(@Query("id") ids: List<Int>): Single<List<ProductEntity>>

}
