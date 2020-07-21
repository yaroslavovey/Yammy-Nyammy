package com.phooper.yammynyammy.presenters.product_list

import com.ph00.domain.usecases.GetProductListByCategoryUseCase
import com.phooper.yammynyammy.presenters.global.BasePresenter
import com.phooper.yammynyammy.utils.Constants
import com.phooper.yammynyammy.utils.addTo
import com.phooper.yammynyammy.utils.toPresentation
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import javax.inject.Inject
import javax.inject.Named

@InjectViewState
class ProductListPresenter @Inject constructor(
    private val getProductListByCategoryUseCase: GetProductListByCategoryUseCase,
    @Named(Constants.CATEGORY) private val category: Int
) : BasePresenter<ProductListView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadProducts()
    }

    fun loadProducts() {

        getProductListByCategoryUseCase
            .execute(category)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe {
                viewState.run {
                    showLoading()
                    hideNoNetwork()
                }
            }
            .doAfterSuccess { viewState.setProductList(it.toPresentation()) }
            .doOnError { viewState.showNoNetwork() }
            .doFinally { viewState.hideLoading() }
            .subscribe()
            .addTo(compositeDisposable)

    }

}