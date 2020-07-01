package com.phooper.yammynyammy.domain.repositories

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.phooper.yammynyammy.data.models.Address
import com.phooper.yammynyammy.data.models.Order
import com.phooper.yammynyammy.data.models.ProductIdAndCount
import com.phooper.yammynyammy.data.models.User

interface UserRepository {

    suspend fun setUserPersonalData(data: User, userUid: String): Boolean?
    suspend fun getUserPersonalData(userUid: String): DocumentSnapshot?

    suspend fun getAddressByUid(uid: String, userUid: String): DocumentSnapshot?
    suspend fun updateAddress(
        address: Address,
        addressUid: String,
        userUid: String
    ): Boolean?

    suspend fun deleteAddressByUid(uid: String, userUid: String): Boolean?
    suspend fun addAddress(address: Address, userUid: String): Boolean?
    suspend fun getAddressesAsCollection(userUid: String): CollectionReference?

    suspend fun addOrder(order: Order, userUid: String): Boolean?
    suspend fun getOrdersList(userUid: String): List<DocumentSnapshot>?
    suspend fun getOrderByUid(orderUid : String, userUid: String): DocumentSnapshot?

    suspend fun decreaseCartProductCount(productId: Int, count: Int)
    suspend fun increaseCartProductCount(productId: Int, count: Int)
    suspend fun deleteCartProductById(productId: Int)
    suspend fun deleteAllCartProducts()
    suspend fun addCartProduct(cartProduct: ProductIdAndCount)
    suspend fun getCartProductById(productId: Int): ProductIdAndCount?
    suspend fun getAllCartProducts(): List<ProductIdAndCount>?

}