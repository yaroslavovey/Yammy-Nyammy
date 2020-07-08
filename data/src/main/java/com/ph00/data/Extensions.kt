package com.ph00.data

import com.ph00.data.entities.ProductEntity
import com.ph00.data.entities.ProductIdAndCountEntity
import com.ph00.domain.models.ProductIdAndCountModel
import com.ph00.domain.models.ProductModel

fun ProductIdAndCountEntity.toModel(): ProductIdAndCountModel =
    ProductIdAndCountModel(productId = productId, count = count)

fun ProductIdAndCountModel.toEntity(): ProductIdAndCountEntity =
    ProductIdAndCountEntity(productId = productId, count = count)

fun ProductEntity.toModel(): ProductModel =
    ProductModel(id = id, title = title, price = price, desc = desc, imageURL = imageURL)
