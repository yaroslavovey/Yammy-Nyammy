package com.phooper.yammynyammy.presenters.product_list

import com.ph00.domain.usecases.GetProductListByCategoryUseCase
import com.phooper.yammynyammy.presenters.global.BasePresenter
import com.phooper.yammynyammy.ui.product_list.ProductListFragment
import moxy.InjectViewState
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class ProductListPresenter @Inject constructor(
    private val getProductListByCategoryUseCase: GetProductListByCategoryUseCase
//    ,
//    @ProductListFragment.CategoryNumber private val category: Int
) : BasePresenter<ProductListView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadProducts()
    }

    fun loadProducts() {
//        getProductListByCategoryUseCase
//            .execute()
        }

}