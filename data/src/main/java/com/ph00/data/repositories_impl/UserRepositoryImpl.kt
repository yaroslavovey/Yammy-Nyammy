package com.ph00.data.repositories_impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.ph00.data.Constants.Companion.ADDRESSES_COLLECTION
import com.ph00.data.Constants.Companion.DELIVERY_PRICE
import com.ph00.data.Constants.Companion.ORDERS_COLLECTION
import com.ph00.data.Constants.Companion.USERS_COLLECTION
import com.ph00.data.db.dao.CartProductsDao
import com.ph00.data.toEntity
import com.ph00.data.toModel
import com.ph00.domain.models.AddressModel
import com.ph00.domain.models.OrderModel
import com.ph00.domain.models.ProductIdAndCountModel
import com.ph00.domain.models.UserModel
import com.ph00.domain.repositories.UserRepository
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

    override suspend fun getUserPersonalData(userUid: String): DocumentSnapshot? {
        return try {
            firebaseFirestone
                .collection(USERS_COLLECTION)
                .document(userUid)
                .get()
                .await()
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getUserPersonalDataAsDocument(userUid: String): DocumentReference? {
        return try {
            firebaseFirestone
                .collection(USERS_COLLECTION)
                .document(userUid)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getAddressByUid(uid: String, userUid: String): DocumentSnapshot? {
        return try {
            firebaseFirestone
                .collection(USERS_COLLECTION)
                .document(userUid)
                .collection(ADDRESSES_COLLECTION)
                .document(uid)
                .get()
                .await()
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
                .set(address)
                .await()
            true
        } catch (e: Exception) {
            null
        }
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
                .add(address)
                .await()
            true
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getAddressesAsCollection(userUid: String): CollectionReference? {
        return try {
            firebaseFirestone
                .collection(USERS_COLLECTION)
                .document(userUid)
                .collection(ADDRESSES_COLLECTION)
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
                .add(order)
                .await()
            true
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getOrdersList(userUid: String): List<DocumentSnapshot>? {
        return try {
            firebaseFirestone
                .collection(USERS_COLLECTION)
                .document(userUid)
                .collection(ORDERS_COLLECTION)
                .orderBy("timestamp")
                .get()
                .await()
                .documents
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getOrderByUid(orderUid: String, userUid: String): DocumentSnapshot? {
        return try {
            firebaseFirestone
                .collection(USERS_COLLECTION)
                .document(userUid)
                .collection(ORDERS_COLLECTION)
                .document(orderUid)
                .get()
                .await()
        } catch (e: Exception) {
            null
        }
    }

    override fun getDeliveryPrice(addressUid: String?): Int = DELIVERY_PRICE

    override suspend fun addCartProduct(cartProduct: ProductIdAndCountModel) =
        cartProductsDao.addToCart(cartProduct.toEntity())

    override suspend fun getAllCartProducts(): List<ProductIdAndCountModel>? =
        cartProductsDao.getAllCartProducts()?.map { it.toModel() }

    override fun getAllCartProductsLiveData(): LiveData<List<ProductIdAndCountModel>> =
        cartProductsDao.getAllCartProductsLiveData().map { list -> list.map { it.toModel() } }

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
}

