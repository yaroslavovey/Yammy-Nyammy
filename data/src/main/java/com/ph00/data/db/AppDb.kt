package com.ph00.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ph00.data.db.dao.CartProductsDao
import com.ph00.data.entities.ProductIdAndCountEntity

@Database(entities = [ProductIdAndCountEntity::class], version = 1)
abstract class AppDb : RoomDatabase() {

    abstract fun getCartProductsDao(): CartProductsDao

}