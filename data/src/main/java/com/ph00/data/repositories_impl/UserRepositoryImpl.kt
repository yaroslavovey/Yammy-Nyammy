package com.ph00.data.repositories_impl

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
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
import hu.akarnokd.rxjava3.bridge.RxJavaBridge
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UserRepositoryImpl
@Inject constructor(
    private val firebaseFirestone: FirebaseFirestore,
    private val cartProductsDao: CartProductsDao
) : UserRepository {

    override fun setUserPersonalData(data: UserModel, userUid: String): Completable =
        Completable.create { emitter ->
            firebaseFirestone
                .collection(USERS_COLLECTION)
                .document(userUid)
                .set(data)
                .addOnFailureListener { emitter.onError(it) }
                .addOnSuccessListener { emitter.onComplete() }
        }

    override fun getUserPersonalData(userUid: String): Single<UserModel> =
        Single.create { emitter ->
            firebaseFirestone
                .collection(USERS_COLLECTION)
                .document(userUid)
                .get()
                .addOnFailureListener { emitter.onError(it) }
                .addOnSuccessListener { emitter.onSuccess(it.toObject<UserModel>()) }

        }

    override fun getAddressByUid(uid: String, userUid: String): Single<AddressModel> =
        Single.create { emitter ->
            firebaseFirestone
                .collection(USERS_COLLECTION)
                .document(userUid)
                .collection(ADDRESSES_COLLECTION)
                .document(uid)
                .get()
                .addOnFailureListener { emitter.onError(it) }
                .addOnSuccessListener { emitter.onSuccess(it.toObject<AddressEntity>()?.toModel()) }
        }

    override fun updateAddress(
        address: AddressModel,
        addressUid: String,
        userUid: String
    ): Completable =

        Completable.create { emitter ->
            firebaseFirestone
                .collection(USERS_COLLECTION)
                .document(userUid)
                .collection(ADDRESSES_COLLECTION)
                .document(addressUid)
                .set(address.toEntity())
                .addOnFailureListener { emitter.onError(it) }
                .addOnSuccessListener { emitter.onComplete() }

        }

    override fun getAllAddresses(userUid: String): Observable<List<AddressModel>> =
        Observable.create { emitter ->

            val eventCollection = firebaseFirestone
                .collection(USERS_COLLECTION)
                .document(userUid)
                .collection(ADDRESSES_COLLECTION)

            val subscription = eventCollection.addSnapshotListener { querySnapshot, _ ->
                querySnapshot
                    ?.documents
                    ?.mapNotNull { it.toObject<AddressEntity>()?.toModel() }
                    ?.let { addressModelList -> emitter.onNext(addressModelList) }
            }

            emitter.setCancellable { subscription.remove() }

        }

    override fun deleteAddressByUid(uid: String, userUid: String): Completable =
        Completable.create { emitter ->
            firebaseFirestone
                .collection(USERS_COLLECTION)
                .document(userUid)
                .collection(ADDRESSES_COLLECTION)
                .document(uid)
                .delete()
                .addOnFailureListener { emitter.onError(it) }
                .addOnSuccessListener { emitter.onComplete() }
        }

    override fun addAddress(address: AddressModel, userUid: String): Completable =
        Completable.create { emitter ->
            firebaseFirestone
                .collection(USERS_COLLECTION)
                .document(userUid)
                .collection(ADDRESSES_COLLECTION)
                .add(address.toEntity())
                .addOnFailureListener { emitter.onError(it) }
                .addOnSuccessListener { emitter.onComplete() }
        }

    override fun addOrder(order: OrderModel, userUid: String): Completable =
        Completable.create { emitter ->
            firebaseFirestone
                .collection(USERS_COLLECTION)
                .document(userUid)
                .collection(ORDERS_COLLECTION)
                .add(order.toEntity())
                .addOnFailureListener { emitter.onError(it) }
                .addOnSuccessListener { emitter.onComplete() }
        }

    override fun getOrdersList(userUid: String): Observable<List<OrderModel>> =
        Observable.create { emitter ->

            val eventCollection = firebaseFirestone
                .collection(USERS_COLLECTION)
                .document(userUid)
                .collection(ORDERS_COLLECTION)
                .orderBy("timestamp", Query.Direction.DESCENDING)

            val subscription = eventCollection.addSnapshotListener { querySnapshot, _ ->
                querySnapshot?.toObjects<OrderEntity>()?.map { it.toModel() }
                    ?.let { emitter.onNext(it) }
            }

            emitter.setCancellable { subscription.remove() }

        }

    override fun getOrderByUid(orderUid: String, userUid: String): Single<OrderModel> =
        Single.create { emitter ->
            firebaseFirestone
                .collection(USERS_COLLECTION)
                .document(userUid)
                .collection(ORDERS_COLLECTION)
                .document(orderUid)
                .get()
                .addOnFailureListener { emitter.onError(it) }
                .addOnSuccessListener { emitter.onSuccess(it.toObject<OrderEntity>()?.toModel()) }
        }

    override fun getDeliveryPrice(addressUid: String?): Single<Int> =
        Single.create { emitter -> emitter.onSuccess(DELIVERY_PRICE) }

    override fun addCartProduct(cartProduct: ProductIdAndCountModel): Completable =
        RxJavaBridge.toV3Completable(cartProductsDao.addToCart(cartProduct.toEntity()))

    override fun getAllCartProducts(): Observable<List<ProductIdAndCountModel>?> =
        RxJavaBridge
            .toV3Observable(
                cartProductsDao.getAllCartProducts().map { list -> list.map { it.toModel() } })

    override fun increaseCartProductCount(productId: Int, count: Int): Completable =
        RxJavaBridge.toV3Completable(cartProductsDao.increaseProductCount(productId, count))

    override fun decreaseCartProductCount(productId: Int, count: Int): Completable =
        RxJavaBridge.toV3Completable(cartProductsDao.decreaseProductCount(productId, count))

    override fun deleteCartProductById(productId: Int): Completable =
        RxJavaBridge.toV3Completable(cartProductsDao.deleteProductById(productId))

    override fun deleteAllCartProducts(): Completable =
        RxJavaBridge.toV3Completable(cartProductsDao.deleteAllCartProducts())

    override fun getCartProductById(productId: Int): Maybe<ProductIdAndCountModel> =
        RxJavaBridge.toV3Maybe(cartProductsDao.getProductById(productId).map { it.toModel() })

}

