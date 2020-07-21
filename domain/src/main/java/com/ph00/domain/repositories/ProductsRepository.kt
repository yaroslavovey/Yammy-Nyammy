package com.ph00.domain.repositories

import com.ph00.domain.models.ProductModel
import io.reactivex.rxjava3.core.Single

interface ProductsRepository {
    fun getProductListByCategory(category: Int): Single<List<ProductModel>>
    fun getProductById(id: Int): Single<ProductModel>
    fun getProductListByIds(ids: List<Int>): Single<List<ProductModel>>
}