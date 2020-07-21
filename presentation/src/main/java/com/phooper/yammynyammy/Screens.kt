package com.phooper.yammynyammy

import com.phooper.yammynyammy.ui.bottom_nav.BottomNavFlowFragment
import com.phooper.yammynyammy.ui.login.LoginFlowFragment
import com.phooper.yammynyammy.ui.add_update_address.AddUpdateAddressFragment
import com.phooper.yammynyammy.ui.cart.CartFragment
import com.phooper.yammynyammy.ui.edit_profile.EditProfileFragment
import com.phooper.yammynyammy.ui.login.LoginFragment
import com.phooper.yammynyammy.ui.make_order.MakeOrderFragment
import com.phooper.yammynyammy.ui.menu.MenuFragment
import com.phooper.yammynyammy.ui.my_addresses.MyAddressesFragment
import com.phooper.yammynyammy.ui.order.OrderFragment
import com.phooper.yammynyammy.ui.orders.OrdersFragment
import com.phooper.yammynyammy.ui.product.ProductFragment
import com.phooper.yammynyammy.ui.product_list.ProductListFragment
import com.phooper.yammynyammy.ui.profile.ProfileFragment
import com.phooper.yammynyammy.ui.register.RegisterFragment
import com.phooper.yammynyammy.ui.update_password.UpdatePasswordFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object LoginFlow : SupportAppScreen() {
        override fun getFragment() =
            LoginFlowFragment()
    }

    object BottomNavFlow : SupportAppScreen() {

        override fun getFragment() =
            BottomNavFlowFragment()
    }
    object Login : SupportAppScreen() {
        override fun getFragment() = LoginFragment()
    }

    object Register : SupportAppScreen() {
        override fun getFragment() =
            RegisterFragment()
    }

    data class AddUpdateAddress(val addressId: String?) : SupportAppScreen() {
        override fun getFragment() = AddUpdateAddressFragment.create(addressId)
    }

    data class Order(val orderId: String) : SupportAppScreen() {
        override fun getFragment() = OrderFragment.create(orderId)
    }

    data class Product(val productId: Int) : SupportAppScreen() {
        override fun getFragment() = ProductFragment.create(productId)
    }

    object ProductList : SupportAppScreen() {
        override fun getFragment() = ProductListFragment()
    }

    object Orders : SupportAppScreen() {
        override fun getFragment() = OrdersFragment()
    }

    object Cart : SupportAppScreen() {
        override fun getFragment() = CartFragment()
    }

    object EditProfile : SupportAppScreen() {
        override fun getFragment() = EditProfileFragment()
    }

    object MakeOrder : SupportAppScreen() {
        override fun getFragment() = MakeOrderFragment()
    }

    object Menu : SupportAppScreen() {
        override fun getFragment() = MenuFragment()
    }

    object MyAddresses : SupportAppScreen() {
        override fun getFragment() = MyAddressesFragment()
    }

    object Profile : SupportAppScreen() {
        override fun getFragment() = ProfileFragment()
    }

    object UpdatePassword : SupportAppScreen() {
        override fun getFragment() = UpdatePasswordFragment()
    }


}