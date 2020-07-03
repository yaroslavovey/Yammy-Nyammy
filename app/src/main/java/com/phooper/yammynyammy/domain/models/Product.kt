package com.phooper.yammynyammy.domain.models

import com.squareup.moshi.Json

data class Product(
    @field:Json(name = "id") val id: Int = 0,
    @field:Json(name = "title") val title: String = "",
    @field:Json(name = "price") val price: Int = 0,
    @field:Json(name = "description") val desc: String = "",
    @field:Json(name = "imageURL") val imageURL: String = ""
)