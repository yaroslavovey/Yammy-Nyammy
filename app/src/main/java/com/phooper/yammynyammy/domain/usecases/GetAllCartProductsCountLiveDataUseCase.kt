package com.phooper.yammynyammy.domain.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default

class GetAllCartProductsCountLiveDataUseCase(private val getAllCartProductsLiveDataUseCase: GetAllCartProductsLiveDataUseCase) {

    fun execute(coroutineScope: CoroutineScope): LiveData<Int> =
        getAllCartProductsLiveDataUseCase.execute().switchMap { listProductIdAndCount ->
            liveData(context = coroutineScope.coroutineContext + Default) {
                emit(listProductIdAndCount.sumBy { it.count })
            }
        }

}