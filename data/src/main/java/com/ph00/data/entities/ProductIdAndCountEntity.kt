package com.ph00.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductIdAndCountEntity(
    @PrimaryKey
    @ColumnInfo(name = "product_id")
    val productId: Int,
    val count: Int
)