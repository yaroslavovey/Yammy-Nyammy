package com.phooper.yammynyammy.data.repositories_impl

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.phooper.yammynyammy.data.db.dao.CartProductsDao
import com.phooper.yammynyammy.domain.models.Address
import com.phooper.yammynyammy.domain.models.Order
import com.phooper.yammynyammy.domain.models.ProductIdAndCount
import com.phooper.yammynyammy.domain.models.User
import com.phooper.yammynyammy.domain.repositories.UserRepository
import com.phooper.yammynyammy.utils.Constants.Companion.ADDRESSES_COLLECTION
import com.phooper.yammynyammy.utils.Constants.Companion.ORDERS_COLLECTION
import com.phooper.yammynyammy.utils.Constants.Companion.USERS_COLLECTION
import kotlinx.coroutines.tasks.await
import timber.log.Timber

class UserRepositoryImpl(
    private val firebaseFirestone: FirebaseFirestore,
    private val cartProductsDao: CartProductsDao
) : UserRepository {

    override suspend fun setUserPersonalData(data: User, userUid: String): Boolean? {
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
        address: Address,
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
            Timber.d(e.toString())
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

    override suspend fun addAddress(address: Address, userUid: String): Boolean? {
        return try {
            firebaseFirestone
                .collection(USERS_COLLECTION)
                .document(userUid)
                .collection(ADDRESSES_COLLECTION)
                .add(address)
                .await()
            true
        } catch (e: Exception) {
            Timber.d(e.toString())
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

    override suspend fun addOrder(order: Order, userUid: String): Boolean? {
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

    override suspend fun addCartProduct(cartProduct: ProductIdAndCount) =
        cartProductsDao.addToCart(cartProduct)

    override suspend fun getAllCartProducts(): List<ProductIdAndCount>? =
        cartProductsDao.getAllCartProducts()

    override fun getAllCartProductsLiveData(): LiveData<List<ProductIdAndCount>> =
        cartProductsDao.getAllCartProductsLiveData()

    override suspend fun increaseCartProductCount(productId: Int, count: Int) =
        cartProductsDao.increaseProductCount(productId, count)

    override suspend fun decreaseCartProductCount(productId: Int, count: Int) =
        cartProductsDao.decreaseProductCount(productId, count)

    override suspend fun deleteCartProductById(productId: Int) =
        cartProductsDao.deleteProductById(productId)

    override suspend fun deleteAllCartProducts() =
        cartProductsDao.deleteAllCartProducts()

    override suspend fun getCartProductById(productId: Int): ProductIdAndCount? =
        cartProductsDao.getProductById(productId)
}

