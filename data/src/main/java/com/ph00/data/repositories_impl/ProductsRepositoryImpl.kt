package com.ph00.data.repositories_impl

import com.ph00.data.api.ShopApi
import com.ph00.data.toModel
import com.ph00.domain.models.ProductModel
import com.ph00.domain.repositories.ProductsRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor
    (private val shopApi: ShopApi) : ProductsRepository {

    override fun getProductListByCategory(category: String): Single<List<ProductModel>> =
        shopApi.getProductListByCategory(category).map { list -> list.map { it.toModel() } }

    override fun getProductById(id: Int): Single<ProductModel> =
        shopApi.getProductById(id).map { it.toModel() }

    override fun getProductListByIds(ids: List<Int>): Single<List<ProductModel>> =
        shopApi.getProductListByIds(ids).map { list -> list.map { it.toModel() } }

}
