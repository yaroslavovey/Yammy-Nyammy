package com.phooper.yammynyammy.presenters.orders

import com.ph00.domain.usecases.GetOrderListUseCase
import com.phooper.yammynyammy.Screens
import com.phooper.yammynyammy.presenters.global.BasePresenter
import com.phooper.yammynyammy.utils.Constants.Companion.FLOW
import com.phooper.yammynyammy.utils.addTo
import com.phooper.yammynyammy.utils.toPresentation
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import ru.terrakok.cicerone.Router
import javax.inject.Inject
import javax.inject.Named

@InjectViewState
class OrdersPresenter
@Inject constructor(
    private val getOrderListUseCase: GetOrderListUseCase,
    @Named(FLOW) private val router: Router
) : BasePresenter<OrdersView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadOrders()
    }

    fun loadOrders() {
        getOrderListUseCase
            .execute()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map { list -> list.map { it.toPresentation() } }
            .doOnSubscribe {
                viewState.run {
                    showLoading()
                    hideNoNetwork()
                    hideNoOrders()
                }
            }
            .doOnNext {
                if (it.isNullOrEmpty()) {
                    viewState.showNoOrders()
                } else {
                    viewState.setOrdersList(it)
                }
            }
            .doAfterNext { viewState.hideLoading() }
            .doOnError { viewState.showNoNetwork() }
            .subscribe()
            .addTo(compositeDisposable)
    }

    fun backToMenu() {
        router.replaceScreen(Screens.Menu)
    }
}