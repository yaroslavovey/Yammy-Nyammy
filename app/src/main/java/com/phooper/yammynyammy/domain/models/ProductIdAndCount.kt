package com.phooper.yammynyammy.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductIdAndCount(
    @PrimaryKey
    @ColumnInfo(name = "product_id")
    val productId: Int,
    val count: Int
)