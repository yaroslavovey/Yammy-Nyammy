package com.ph00.data.repositories_impl

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.ph00.data.Constants.Companion.ADDRESSES_COLLECTION
import com.ph00.data.Constants.Companion.DELIVERY_PRICE
import com.ph00.data.Constants.Companion.ORDERS_COLLECTION
import com.ph00.data.Constants.Companion.USERS_COLLECTION
import com.ph00.data.applyTwoRetriesOnError
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
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

@ExperimentalCoroutinesApi
class UserRepositoryImpl(
    private val firebaseFirestone: FirebaseFirestore,
    private val cartProductsDao: CartProductsDao
) : UserRepository {

    override fun setUserPersonalData(data: UserModel, userUid: String): Flow<Unit> = flow {
        firebaseFirestone
            .collection(USERS_COLLECTION)
            .document(userUid)
            .set(data)
            .await()
            .let { emit(Unit) }
    }.applyTwoRetriesOnError()

    override fun getUserPersonalData(userUid: String): Flow<UserModel> = flow {
        firebaseFirestone
            .collection(USERS_COLLECTION)
            .document(userUid)
            .get()
            .await()
            .toObject<UserModel>()
            ?.let { emit(it) }
    }.applyTwoRetriesOnError()

    override fun getAddressByUid(uid: String, userUid: String): Flow<AddressModel> = flow {
        firebaseFirestone
            .collection(USERS_COLLECTION)
            .document(userUid)
            .collection(ADDRESSES_COLLECTION)
            .document(uid)
            .get()
            .await()
            .toObject<AddressEntity>()
            ?.toModel()
            ?.let { emit(it) }
    }.applyTwoRetriesOnError()

    override fun updateAddress(address: AddressModel, addressUid: String, userUid: String) = flow {
        firebaseFirestone
            .collection(USERS_COLLECTION)
            .document(userUid)
            .collection(ADDRESSES_COLLECTION)
            .document(addressUid)
            .set(address.toEntity())
            .await()
            .let { emit(Unit) }
    }.applyTwoRetriesOnError()

    @ExperimentalCoroutinesApi
    override fun getAllAddresses(userUid: String): Flow<List<AddressModel>> =
        callbackFlow {
            val eventCollection = firebaseFirestone
                .collection(USERS_COLLECTION)
                .document(userUid)
                .collection(ADDRESSES_COLLECTION)

            val subscription = eventCollection.addSnapshotListener { querySnapshot, _ ->
                querySnapshot
                    ?.documents
                    ?.mapNotNull { it.toObject<AddressEntity>()?.toModel() }
                    ?.let { addressModelList -> offer(addressModelList) }
            }

            awaitClose { subscription.remove() }

        }.applyTwoRetriesOnError()


    override fun deleteAddressByUid(uid: String, userUid: String) = flow {
        firebaseFirestone
            .collection(USERS_COLLECTION)
            .document(userUid)
            .collection(ADDRESSES_COLLECTION)
            .document(uid)
            .delete()
            .await()
            .let { emit(Unit) }
    }.applyTwoRetriesOnError()

    override fun addAddress(address: AddressModel, userUid: String) =
        flow {
            firebaseFirestone
                .collection(USERS_COLLECTION)
                .document(userUid)
                .collection(ADDRESSES_COLLECTION)
                .add(address.toEntity())
                .await()
                .let { emit(Unit) }
        }.applyTwoRetriesOnError()

    override fun addOrder(order: OrderModel, userUid: String): Flow<Unit> = flow {
        firebaseFirestone
            .collection(USERS_COLLECTION)
            .document(userUid)
            .collection(ORDERS_COLLECTION)
            .add(order.toEntity())
            .await()
            ?.let { emit(Unit) }
    }.applyTwoRetriesOnError()

    override fun getOrdersList(userUid: String): Flow<List<OrderModel>> = callbackFlow {
        val eventCollection = firebaseFirestone
            .collection(USERS_COLLECTION)
            .document(userUid)
            .collection(ORDERS_COLLECTION)
            .orderBy("timestamp", Query.Direction.DESCENDING)

        val subscription = eventCollection.addSnapshotListener { querySnapshot, _ ->
            querySnapshot?.toObjects<OrderEntity>()?.map { it.toModel() }?.let { offer(it) }
        }

        awaitClose { subscription.remove() }

    }.applyTwoRetriesOnError()

    override fun getOrderByUid(orderUid: String, userUid: String) = flow {
        firebaseFirestone
            .collection(USERS_COLLECTION)
            .document(userUid)
            .collection(ORDERS_COLLECTION)
            .document(orderUid)
            .get()
            .await()
            .toObject<OrderEntity>()
            ?.toModel()
            ?.let { emit(it) }
    }.applyTwoRetriesOnError()

    override fun getDeliveryPrice(addressUid: String?): Flow<Int> = flow { emit(DELIVERY_PRICE) }

    override fun addCartProduct(cartProduct: ProductIdAndCountModel): Flow<Unit> = flow {
        emit(cartProductsDao.addToCart(cartProduct.toEntity()))
    }

    override fun getAllCartProducts(): Flow<List<ProductIdAndCountModel>?> =
        cartProductsDao.getAllCartProducts().map { list -> list?.map { it.toModel() } }

    override fun increaseCartProductCount(productId: Int, count: Int): Flow<Unit> = flow {
        emit(cartProductsDao.increaseProductCount(productId, count))
    }

    override fun decreaseCartProductCount(productId: Int, count: Int): Flow<Unit> = flow {
        emit(cartProductsDao.decreaseProductCount(productId, count))
    }

    override fun deleteCartProductById(productId: Int): Flow<Unit> = flow {
        emit(cartProductsDao.deleteProductById(productId))
    }

    override fun deleteAllCartProducts(): Flow<Unit> = flow {
        emit(cartProductsDao.deleteAllCartProducts())
    }

    override fun getCartProductById(productId: Int): Flow<ProductIdAndCountModel?> = flow {
        emit(cartProductsDao.getProductById(productId)?.toModel())
    }

}

