package com.ph00.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ph00.data.entities.ProductIdAndCountEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartProductsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addToCart(cartProduct: ProductIdAndCountEntity)

    @Query("SELECT * FROM products WHERE product_id = :id")
    fun getProductById(id: Int): ProductIdAndCountEntity?

    @Query("DELETE FROM products WHERE product_id = :id")
    fun deleteProductById(id: Int)

    @Query("UPDATE products SET count = count + :count WHERE product_id = :productId")
    fun increaseProductCount(productId: Int, count: Int)

    @Query("UPDATE products SET count = count - :count WHERE product_id = :productId")
    fun decreaseProductCount(productId: Int, count: Int)

    @Query("SELECT * FROM products")
    fun getAllCartProducts(): Flow<List<ProductIdAndCountEntity>?>

    @Query("DELETE FROM products")
    fun deleteAllCartProducts()

}