package com.phooper.yammynyammy.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.phooper.yammynyammy.data.models.ProductIdAndCount

@Dao
interface CartProductsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToCart(cartProduct: ProductIdAndCount)

    @Query("SELECT * FROM products WHERE product_id = :id")
    suspend fun getProductById(id: Int): ProductIdAndCount?

    @Query("DELETE FROM products WHERE product_id = :id")
    suspend fun deleteProductById(id: Int)

    @Query("UPDATE products SET count = count + :count WHERE product_id = :productId")
    suspend fun increaseProductCount(productId: Int, count: Int)

    @Query("UPDATE products SET count = count - :count WHERE product_id = :productId")
    suspend fun decreaseProductCount(productId: Int, count: Int)

    @Query("SELECT * FROM products")
    fun getAll(): LiveData<List<ProductIdAndCount>>

}