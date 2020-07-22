package com.phooper.yammynyammy.ui.cart

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.livermor.delegateadapter.delegate.diff.DiffUtilCompositeAdapter
import com.livermor.delegateadapter.delegate.diff.KDiffUtilItem
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.presenters.cart.CartPresenter
import com.phooper.yammynyammy.presenters.cart.CartView
import com.phooper.yammynyammy.ui.global.BaseFragment
import com.phooper.yammynyammy.ui.global.adapters.CartProductDelegateAdapter
import com.phooper.yammynyammy.ui.global.adapters.TotalPriceDelegateAdapter
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.no_network_layout.*
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class CartFragment : BaseFragment(), CartView {

    override val layoutRes = R.layout.fragment_cart

    @Inject
    lateinit var presenterProvider: Provider<CartPresenter>

    private val presenter by moxyPresenter { presenterProvider.get() }

    @Inject
    lateinit var cartProductDelegateAdapter: CartProductDelegateAdapter

    private val delegateAdapter by lazy {
        DiffUtilCompositeAdapter.Builder()
            .add(cartProductDelegateAdapter)
            .add(TotalPriceDelegateAdapter())
            .build()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        with(cartProductDelegateAdapter) {
            onMinusClickListener = { presenter.removeProductFromCart(it) }
            onPlusClickListener = { presenter.addProductToCart(it) }
        }

        make_order_btn.setOnClickListener {
            presenter.makeOrder()
        }

        recycler_view.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = delegateAdapter
        }

        refresh_btn.setOnClickListener {
            presenter.loadCartProducts()
        }

        go_back_to_menu_btn.setOnClickListener {
            presenter.backToMenu()
        }
    }

    override fun setCartProductList(list: List<KDiffUtilItem>) {
        delegateAdapter.swapData(list)
    }

    override fun showLoading() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress_bar.visibility = View.GONE
    }

    override fun showNoNetwork() {
        no_network_layout.visibility = View.VISIBLE
    }

    override fun hideNoNetwork() {
        no_network_layout.visibility = View.GONE
    }

    override fun showNoProductsInCart() {
        no_products_in_cart_layout.visibility = View.VISIBLE
    }

    override fun hideNoProductsInCart() {
        no_products_in_cart_layout.visibility = View.GONE
    }

    override fun showMakeOrderButton() {
        make_order_btn.visibility = View.VISIBLE
    }

    override fun hideMakeOrderButton() {
        make_order_btn.visibility = View.GONE
    }
}