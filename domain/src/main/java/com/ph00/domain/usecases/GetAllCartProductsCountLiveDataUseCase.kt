package com.ph00.domain.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default

class GetAllCartProductsCountLiveDataUseCase(private val getAllCartProductIdAndCountLiveDataUseCase: GetAllCartProductIdAndCountLiveDataUseCase) {

    fun execute(coroutineScope: CoroutineScope): LiveData<Int> =
        getAllCartProductIdAndCountLiveDataUseCase.execute().switchMap { listProductIdAndCount ->
            liveData(context = coroutineScope.coroutineContext + Default) {
                emit(listProductIdAndCount.sumBy { it.count })
            }
        }

}