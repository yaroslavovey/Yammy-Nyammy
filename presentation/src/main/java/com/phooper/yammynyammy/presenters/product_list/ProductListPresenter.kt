package com.phooper.yammynyammy.presenters.product_list

import com.ph00.domain.usecases.GetProductListByCategoryUseCase
import com.phooper.yammynyammy.presenters.global.BasePresenter
import com.phooper.yammynyammy.utils.addTo
import com.phooper.yammynyammy.utils.toPresentation
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState

@InjectViewState
class ProductListPresenter @AssistedInject constructor(
    private val getProductListByCategoryUseCase: GetProductListByCategoryUseCase,
    @Assisted private val category: Int
) : BasePresenter<ProductListView>() {

    @AssistedInject.Factory
    interface Factory {
        fun create(category: Int): ProductListPresenter
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadProducts()
    }

    fun loadProducts() {
        getProductListByCategoryUseCase
            .execute(category)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map { list -> list.map { it.toPresentation() } }
            .doOnSubscribe {
                viewState.run {
                    showLoading()
                    hideNoNetwork()
                }
            }
            .doAfterSuccess { viewState.setProductList(it) }
            .doOnError { viewState.showNoNetwork() }
            .doFinally { viewState.hideLoading() }
            .subscribe()
            .addTo(compositeDisposable)
    }
}