package com.ph00.data.repositories_impl

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.ph00.data.Constants.Companion.ADDRESSES_COLLECTION
import com.ph00.data.Constants.Companion.DELIVERY_PRICE
import com.ph00.data.Constants.Companion.ORDERS_COLLECTION
import com.ph00.data.Constants.Companion.USERS_COLLECTION
import com.ph00.data.db.dao.CartProductsDao
import com.ph00.data.entities.AddressEntity
import com.ph00.data.entities.OrderEntity
import com.ph00.data.toEntity
import com.ph00.data.toModel
import com.ph00.domain.models.AddressModel
import com.ph00.domain.models.OrderModel
import com.ph00.domain.models.ProductIdAndCountModel
import com.ph00.domain.models.UserModel
import com.ph00.domain.repositories.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class UserRepositoryImpl(
    private val firebaseFirestone: FirebaseFirestore,
    private val cartProductsDao: CartProductsDao
) : UserRepository {

    override suspend fun setUserPersonalData(data: UserModel, userUid: String): Boolean? {
        return try {
            firebaseFirestone
                .collection(USERS_COLLECTION)
                .document(userUid)
                .set(data)
                .await()
            true
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getUserPersonalData(userUid: String): UserModel? {
        return try {
            firebaseFirestone
                .collection(USERS_COLLECTION)
                .document(userUid)
                .get()
                .await()
                .toObject<UserModel>()
        } catch (e: Exception) {
            null
        }
    }

    @ExperimentalCoroutinesApi
    override fun getUserPersonalDataAsFlow(userUid: String): Flow<UserModel?> =
        callbackFlow {
            val eventDocument = firebaseFirestone
                .collection(USERS_COLLECTION)
                .document(userUid)

            val subscription = eventDocument.addSnapshotListener { querySnapshot, _ ->
                offer(querySnapshot?.toObject<UserModel>())
            }

            awaitClose { subscription.remove() }
        }

    override suspend fun getAddressByUid(uid: String, userUid: String): AddressModel? {
        return try {
            firebaseFirestone
                .collection(USERS_COLLECTION)
                .document(userUid)
                .collection(ADDRESSES_COLLECTION)
                .document(uid)
                .get()
                .await()
                .toObject<AddressEntity>()
                ?.toModel()
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun updateAddress(
        address: AddressModel,
        addressUid: String,
        userUid: String
    ): Boolean? {
        return try {
            firebaseFirestone
                .collection(USERS_COLLECTION)
                .document(userUid)
                .collection(ADDRESSES_COLLECTION)
                .document(addressUid)
                .set(address.toEntity())
                .await()
            true
        } catch (e: Exception) {
            null
        }
    }

    @ExperimentalCoroutinesApi
    override fun getAllAddressesAsFlow(userUid: String): Flow<List<AddressModel>?> =
        callbackFlow {
            val eventCollection = firebaseFirestone
                .collection(USERS_COLLECTION)
                .document(userUid)
                .collection(ADDRESSES_COLLECTION)

            val subscription = eventCollection.addSnapshotListener { querySnapshot, _ ->
                offer(querySnapshot?.documents?.mapNotNull {
                    it.toObject<AddressEntity>()?.toModel()
                })
            }

            awaitClose { subscription.remove() }
        }


    override suspend fun deleteAddressByUid(uid: String, userUid: String): Boolean? {
        return try {
            firebaseFirestone
                .collection(USERS_COLLECTION)
                .document(userUid)
                .collection(ADDRESSES_COLLECTION)
                .document(uid)
                .delete()
                .await()
            true
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun addAddress(address: AddressModel, userUid: String): Boolean? {
        return try {
            firebaseFirestone
                .collection(USERS_COLLECTION)
                .document(userUid)
                .collection(ADDRESSES_COLLECTION)
                .add(address.toEntity())
                .await()
            true
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun addOrder(order: OrderModel, userUid: String): Boolean? {
        return try {
            firebaseFirestone
                .collection(USERS_COLLECTION)
                .document(userUid)
                .collection(ORDERS_COLLECTION)
                .add(order.toEntity())
                .await()
            true
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getOrdersList(userUid: String): List<OrderModel>? {
        return try {
            firebaseFirestone
                .collection(USERS_COLLECTION)
                .document(userUid)
                .collection(ORDERS_COLLECTION)
                .orderBy("timestamp")
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject<OrderEntity>()?.toModel() }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getOrderByUid(orderUid: String, userUid: String): OrderModel? {
        return try {
            firebaseFirestone
                .collection(USERS_COLLECTION)
                .document(userUid)
                .collection(ORDERS_COLLECTION)
                .document(orderUid)
                .get()
                .await()
                .toObject<OrderEntity>()
                ?.toModel()
        } catch (e: Exception) {
            null
        }
    }

    override fun getDeliveryPrice(addressUid: String?): Int = DELIVERY_PRICE

    override suspend fun addCartProduct(cartProduct: ProductIdAndCountModel) =
        cartProductsDao.addToCart(cartProduct.toEntity())

    override suspend fun getAllCartProducts(): List<ProductIdAndCountModel>? =
        cartProductsDao.getAllCartProducts()?.map { it.toModel() }

    override suspend fun increaseCartProductCount(productId: Int, count: Int) =
        cartProductsDao.increaseProductCount(productId, count)

    override suspend fun decreaseCartProductCount(productId: Int, count: Int) =
        cartProductsDao.decreaseProductCount(productId, count)

    override suspend fun deleteCartProductById(productId: Int) =
        cartProductsDao.deleteProductById(productId)

    override suspend fun deleteAllCartProducts() =
        cartProductsDao.deleteAllCartProducts()

    override suspend fun getCartProductById(productId: Int): ProductIdAndCountModel? =
        cartProductsDao.getProductById(productId)?.toModel()

    override fun getAllCartProductsFlow(): Flow<List<ProductIdAndCountModel>> =
        cartProductsDao.getAllCartProductsFlow().map { flow -> flow.map { it.toModel() } }

}

