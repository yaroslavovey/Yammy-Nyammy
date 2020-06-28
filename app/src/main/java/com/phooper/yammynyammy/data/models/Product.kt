package com.phooper.yammynyammy.data.models

import com.squareup.moshi.Json

data class Product(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "title") val title: String,
    @field:Json(name = "price") val price: Int,
    @field:Json(name = "description") val desc: String,
    @field:Json(name = "imageURL") val imageURL: String
) {
}