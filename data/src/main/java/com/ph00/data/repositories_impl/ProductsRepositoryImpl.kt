package com.ph00.data.repositories_impl

import com.ph00.data.api.ShopApi
import com.ph00.data.applyTwoRetriesOnError
import com.ph00.data.toModel
import com.ph00.domain.models.ProductModel
import com.ph00.domain.repositories.ProductsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@ExperimentalCoroutinesApi
class ProductsRepositoryImpl(private val shopApi: ShopApi) : ProductsRepository {

    override fun getProductListByCategory(category: String): Flow<List<ProductModel>> =
        flow { emit(shopApi.getProductListByCategory(category).map { it.toModel() }) }
            .applyTwoRetriesOnError()

    override fun getProductById(id: Int): Flow<ProductModel> =
        flow { emit(shopApi.getProductById(id).toModel()) }
            .applyTwoRetriesOnError()

    override fun getProductListByIds(ids: List<Int>) =
        flow { emit(shopApi.getProductListByIds(ids).map { it.toModel() }) }
            .applyTwoRetriesOnError()

}