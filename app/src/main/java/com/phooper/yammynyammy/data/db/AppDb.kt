package com.phooper.yammynyammy.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.phooper.yammynyammy.data.db.dao.CartProductsDao
import com.phooper.yammynyammy.domain.models.ProductIdAndCount

@Database(entities = [ProductIdAndCount::class], version = 1)
abstract class AppDb : RoomDatabase() {

    abstract fun getCartProductsDao(): CartProductsDao

}