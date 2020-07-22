package com.phooper.yammynyammy.presenters.cart

import com.livermor.delegateadapter.delegate.diff.KDiffUtilItem
import com.ph00.domain.usecases.AddProductsToCartUseCase
import com.ph00.domain.usecases.GetAllProductsInCartUseCase
import com.ph00.domain.usecases.GetDeliveryPriceUseCase
import com.ph00.domain.usecases.RemoveProductsFromCartUseCase
import com.phooper.yammynyammy.Screens
import com.phooper.yammynyammy.entities.CartProduct
import com.phooper.yammynyammy.entities.TotalAndDeliveryPrice
import com.phooper.yammynyammy.presenters.global.BasePresenter
import com.phooper.yammynyammy.utils.Constants.Companion.FLOW
import com.phooper.yammynyammy.utils.addTo
import com.phooper.yammynyammy.utils.toPresentation
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import ru.terrakok.cicerone.Router
import javax.inject.Inject
import javax.inject.Named

@InjectViewState
class CartPresenter
@Inject constructor(
    private val getAllProductsInCartUseCase: GetAllProductsInCartUseCase,
    private val removeProductsFromCartUseCase: RemoveProductsFromCartUseCase,
    private val addProductsToCartUseCase: AddProductsToCartUseCase,
    private val getDeliveryPriceUseCase: GetDeliveryPriceUseCase,
    @Named(FLOW) private val router: Router
) : BasePresenter<CartView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadCartProducts()
    }

    fun backToMenu() {
        router.replaceScreen(Screens.Menu)
    }

    fun loadCartProducts() {
        getAllProductsInCartUseCase
            .execute()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())
            .map { list -> list.map { it.toPresentation() } }
            .zipWith(getDeliveryPriceUseCase.execute().toObservable(),
                BiFunction<List<CartProduct>, Int, List<KDiffUtilItem>>
                { list, deliveryPrice -> prepareCartProductList(list, deliveryPrice) })
            .doOnSubscribe {
                viewState.run {
                    showLoading()
                    hideNoNetwork()
                    hideNoProductsInCart()
                    hideMakeOrderButton()
                }
            }
            .doOnNext { list ->
                if (list.isNullOrEmpty()) {
                    viewState.showNoProductsInCart()
                } else {
                    viewState.run {
                        setCartProductList(list)
                        showMakeOrderButton()
                    }
                }
            }
            .doAfterNext { viewState.hideLoading() }
            .doOnError { viewState.showNoNetwork() }
            .subscribe()
            .addTo(compositeDisposable)
    }

    fun removeProductFromCart(productId: Int) {
        removeProductsFromCartUseCase
            .execute(productId, 1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { viewState.showLoading() }
    }

    fun addProductToCart(productId: Int) {
        addProductsToCartUseCase
            .execute(productId, 1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { viewState.showLoading() }
    }

    fun makeOrder() {
        TODO("Not yet implemented")
    }

    private fun prepareCartProductList(
        list: List<CartProduct>,
        deliveryPrice: Int
    ): List<KDiffUtilItem> {
        if (list.isNullOrEmpty()) return emptyList()
        return list.plus(getDeliveryAndTotalPrice(list, deliveryPrice))
    }

    private fun getDeliveryAndTotalPrice(
        list: List<CartProduct>,
        deliveryPrice: Int
    ): KDiffUtilItem =
        TotalAndDeliveryPrice(
            deliveryPrice = deliveryPrice,
            totalPrice = list.sumBy { it.totalPrice })

}


