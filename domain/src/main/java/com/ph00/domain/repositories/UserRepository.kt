package com.ph00.domain.repositories

import com.ph00.domain.models.AddressModel
import com.ph00.domain.models.OrderModel
import com.ph00.domain.models.ProductIdAndCountModel
import com.ph00.domain.models.UserModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface UserRepository {

    fun setUserPersonalData(data: UserModel, userUid: String): Completable
    fun getUserPersonalData(userUid: String): Single<UserModel>

    fun getAddressByUid(uid: String, userUid: String): Single<AddressModel>
    fun updateAddress(address: AddressModel, addressUid: String, userUid: String): Completable

    fun deleteAddressByUid(uid: String, userUid: String): Completable
    fun addAddress(address: AddressModel, userUid: String): Completable
    fun getAllAddresses(userUid: String): Observable<List<AddressModel>>

    fun addOrder(order: OrderModel, userUid: String): Completable
    fun getOrdersList(userUid: String): Observable<List<OrderModel>>
    fun getOrderByUid(orderUid: String, userUid: String): Single<OrderModel>

    fun getDeliveryPrice(addressUid: String?): Single<Int>

    fun decreaseCartProductCount(productId: Int, count: Int): Completable
    fun increaseCartProductCount(productId: Int, count: Int): Completable
    fun deleteCartProductById(productId: Int): Completable
    fun deleteAllCartProducts(): Completable
    fun addCartProduct(cartProduct: ProductIdAndCountModel): Completable
    fun getCartProductById(productId: Int): Maybe<ProductIdAndCountModel>
    fun getAllCartProducts(): Observable<List<ProductIdAndCountModel>?>

}