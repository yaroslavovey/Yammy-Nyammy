package com.ph00.domain.models

data class CartProductModel(
    val product: ProductModel = ProductModel(),
    val count: Int = 0,
    //Not sure about that...
    val totalPrice: Int = product.price * count
)