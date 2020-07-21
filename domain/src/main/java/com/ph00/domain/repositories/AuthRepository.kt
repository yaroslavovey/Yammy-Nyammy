package com.ph00.domain.repositories

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface AuthRepository {
    fun getCurrentUserUid(): Single<String>
    fun getCurrentUserEmail(): Single<String>
    fun signOut(): Completable
    fun getIsUserSignedInObservable(): Observable<Boolean>
    fun signInViaEmailAndPassword(email: String, password: String): Completable
    fun signUpViaEmailAndPassword(email: String, password: String): Completable
    fun updatePassword(newPassword: String): Completable
    fun reauthenticate(email: String, password: String): Completable
}