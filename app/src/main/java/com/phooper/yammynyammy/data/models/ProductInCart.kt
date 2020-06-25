package com.phooper.yammynyammy.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductInCart(
    @PrimaryKey
    @ColumnInfo(name = "product_id")
    val productId: Int,
    val count: Int
)