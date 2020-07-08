package com.ph00.domain.repositories

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.ph00.domain.models.AddressModel
import com.ph00.domain.models.OrderModel
import com.ph00.domain.models.ProductIdAndCountModel
import com.ph00.domain.models.UserModel

interface UserRepository {

    suspend fun setUserPersonalData(data: UserModel, userUid: String): Boolean?
    suspend fun getUserPersonalData(userUid: String): DocumentSnapshot?
    suspend fun getUserPersonalDataAsDocument(userUid: String): DocumentReference?

    suspend fun getAddressByUid(uid: String, userUid: String): DocumentSnapshot?
    suspend fun updateAddress(
        address: AddressModel,
        addressUid: String,
        userUid: String
    ): Boolean?

    suspend fun deleteAddressByUid(uid: String, userUid: String): Boolean?
    suspend fun addAddress(address: AddressModel, userUid: String): Boolean?
    suspend fun getAddressesAsCollection(userUid: String): CollectionReference?

    suspend fun addOrder(order: OrderModel, userUid: String): Boolean?
    suspend fun getOrdersList(userUid: String): List<DocumentSnapshot>?
    suspend fun getOrderByUid(orderUid: String, userUid: String): DocumentSnapshot?

    fun getDeliveryPrice(addressUid: String?): Int

    suspend fun decreaseCartProductCount(productId: Int, count: Int)
    suspend fun increaseCartProductCount(productId: Int, count: Int)
    suspend fun deleteCartProductById(productId: Int)
    suspend fun deleteAllCartProducts()
    suspend fun addCartProduct(cartProduct: ProductIdAndCountModel)
    suspend fun getCartProductById(productId: Int): ProductIdAndCountModel?
    suspend fun getAllCartProducts(): List<ProductIdAndCountModel>?
    fun getAllCartProductsLiveData(): LiveData<List<ProductIdAndCountModel>>

}