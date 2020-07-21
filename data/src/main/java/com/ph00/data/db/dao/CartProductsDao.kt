package com.ph00.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ph00.data.entities.ProductIdAndCountEntity
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable

@Dao
interface CartProductsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addToCart(cartProduct: ProductIdAndCountEntity): Completable

    @Query("SELECT * FROM products WHERE product_id = :id")
    fun getProductById(id: Int): Maybe<ProductIdAndCountEntity>

    @Query("DELETE FROM products WHERE product_id = :id")
    fun deleteProductById(id: Int): Completable

    @Query("UPDATE products SET count = count + :count WHERE product_id = :productId")
    fun increaseProductCount(productId: Int, count: Int): Completable

    @Query("UPDATE products SET count = count - :count WHERE product_id = :productId")
    fun decreaseProductCount(productId: Int, count: Int): Completable

    @Query("SELECT * FROM products")
    fun getAllCartProducts(): Observable<List<ProductIdAndCountEntity>?>

    @Query("DELETE FROM products")
    fun deleteAllCartProducts(): Completable

}