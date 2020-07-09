package com.ph00.domain.repositories

import com.ph00.domain.models.AddressModel
import com.ph00.domain.models.OrderModel
import com.ph00.domain.models.ProductIdAndCountModel
import com.ph00.domain.models.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun setUserPersonalData(data: UserModel, userUid: String): Boolean?
    suspend fun getUserPersonalData(userUid: String): UserModel?
    fun getUserPersonalDataAsFlow(userUid: String): Flow<UserModel?>

    suspend fun getAddressByUid(uid: String, userUid: String): AddressModel?
    suspend fun updateAddress(address: AddressModel, addressUid: String, userUid: String): Boolean?

    suspend fun deleteAddressByUid(uid: String, userUid: String): Boolean?
    suspend fun addAddress(address: AddressModel, userUid: String): Boolean?
    fun getAllAddressesAsFlow(userUid: String): Flow<List<AddressModel>?>

    suspend fun addOrder(order: OrderModel, userUid: String): Boolean?
    suspend fun getOrdersList(userUid: String): List<OrderModel>?
    suspend fun getOrderByUid(orderUid: String, userUid: String): OrderModel?

    fun getDeliveryPrice(addressUid: String?): Int

    suspend fun decreaseCartProductCount(productId: Int, count: Int)
    suspend fun increaseCartProductCount(productId: Int, count: Int)
    suspend fun deleteCartProductById(productId: Int)
    suspend fun deleteAllCartProducts()
    suspend fun addCartProduct(cartProduct: ProductIdAndCountModel)
    suspend fun getCartProductById(productId: Int): ProductIdAndCountModel?
    suspend fun getAllCartProducts(): List<ProductIdAndCountModel>?
    fun getAllCartProductsFlow(): Flow<List<ProductIdAndCountModel>?>

}