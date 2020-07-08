package com.phooper.yammynyammy.entities

data class Product(
    val id: Int,
    val title: String,
    val price: Int,
    val desc: String,
    val imageURL: String
)