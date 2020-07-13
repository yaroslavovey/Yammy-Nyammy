package com.ph00.domain.repositories

import com.ph00.domain.models.AddressModel
import com.ph00.domain.models.OrderModel
import com.ph00.domain.models.ProductIdAndCountModel
import com.ph00.domain.models.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun setUserPersonalData(data: UserModel, userUid: String): Flow<Unit>
    fun getUserPersonalData(userUid: String): Flow<UserModel>

    fun getAddressByUid(uid: String, userUid: String): Flow<AddressModel>
    fun updateAddress(
        address: AddressModel,
        addressUid: String,
        userUid: String
    ): Flow<Unit>

    fun deleteAddressByUid(uid: String, userUid: String): Flow<Unit>
    fun addAddress(address: AddressModel, userUid: String): Flow<Unit>
    fun getAllAddresses(userUid: String): Flow<List<AddressModel>>

    fun addOrder(order: OrderModel, userUid: String): Flow<Unit>
    fun getOrdersList(userUid: String): Flow<List<OrderModel>>
    fun getOrderByUid(orderUid: String, userUid: String): Flow<OrderModel>

    fun getDeliveryPrice(addressUid: String?): Flow<Int>

    fun decreaseCartProductCount(productId: Int, count: Int) : Flow<Unit>
    fun increaseCartProductCount(productId: Int, count: Int) : Flow<Unit>
    fun deleteCartProductById(productId: Int) : Flow<Unit>
    fun deleteAllCartProducts() : Flow<Unit>
    fun addCartProduct(cartProduct: ProductIdAndCountModel) : Flow<Unit>
    fun getCartProductById(productId: Int): Flow<ProductIdAndCountModel?>
    fun getAllCartProducts(): Flow<List<ProductIdAndCountModel>?>

}