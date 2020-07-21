package com.ph00.data.repositories_impl

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.ph00.domain.repositories.AuthRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) :
    AuthRepository {

    override fun getCurrentUserUid(): Single<String> =
        Single.create { emitter -> firebaseAuth.currentUser?.uid?.let { emitter.onSuccess(it) } }

    override fun getCurrentUserEmail(): Single<String> =
        Single.create { emitter -> firebaseAuth.currentUser?.email?.let { emitter.onSuccess(it) } }

    override fun getIsUserSignedInObservable(): Observable<Boolean> =
        Observable.create { emitter ->
            val subscription = FirebaseAuth.AuthStateListener { auth ->
                emitter.onNext(auth.currentUser != null)
            }

            firebaseAuth.addAuthStateListener(subscription)

            emitter.setCancellable { firebaseAuth.removeAuthStateListener(subscription) }
        }

    override fun signInViaEmailAndPassword(email: String, password: String): Completable =
        Completable.create { emitter ->
            firebaseAuth
                .signInWithEmailAndPassword(email, password)
                .addOnFailureListener { emitter.onError(it) }
                .addOnSuccessListener { emitter.onComplete() }
        }

    override fun signOut(): Completable =
        Completable.create { emitter ->
            firebaseAuth.signOut().let { emitter.onComplete() }
        }

    override fun signUpViaEmailAndPassword(email: String, password: String): Completable =
        Completable.create { emitter ->
            firebaseAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnFailureListener { emitter.onError(it) }
                .addOnSuccessListener { emitter.onComplete() }
        }

    override fun updatePassword(newPassword: String): Completable =
        Completable.create { emitter ->
            firebaseAuth
                .currentUser
                ?.updatePassword(newPassword)
                ?.addOnFailureListener { emitter.onError(it) }
                ?.addOnSuccessListener { emitter.onComplete() }
        }

    override fun reauthenticate(email: String, password: String): Completable =
        Completable.create { emitter ->

            val credential: AuthCredential = EmailAuthProvider.getCredential(email, password)

            firebaseAuth
                .currentUser
                ?.reauthenticate(credential)
                ?.addOnFailureListener { emitter.onError(it) }
                ?.addOnSuccessListener { emitter.onComplete() }
        }

}