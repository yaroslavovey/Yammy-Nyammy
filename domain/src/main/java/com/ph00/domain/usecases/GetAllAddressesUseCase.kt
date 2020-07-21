package com.ph00.domain.usecases

import com.ph00.domain.models.AddressModel
import com.ph00.domain.repositories.UserRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class GetAllAddressesUseCase@Inject constructor(
    private val userRepository: UserRepository,
    private val getCurrentUserUidUseCase: GetCurrentUserUidUseCase
) {

    fun execute(): Observable<List<AddressModel>> =
        getCurrentUserUidUseCase.execute().flatMapObservable { userUid ->
            userRepository.getAllAddresses(userUid)
        }

}