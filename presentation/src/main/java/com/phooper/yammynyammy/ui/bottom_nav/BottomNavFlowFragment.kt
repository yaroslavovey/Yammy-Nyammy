package com.phooper.yammynyammy.ui.bottom_nav

import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.Screens
import com.phooper.yammynyammy.ui.cart.CartFragment
import com.phooper.yammynyammy.ui.global.FlowFragment
import com.phooper.yammynyammy.ui.menu.MenuFragment
import com.phooper.yammynyammy.ui.orders.OrdersFragment
import com.phooper.yammynyammy.ui.profile.ProfileFragment
import com.phooper.yammynyammy.utils.Constants.Companion.FLOW
import com.phooper.yammynyammy.utils.setLaunchScreen
import com.phooper.yammynyammy.utils.showAppBar
import kotlinx.android.synthetic.main.fragment_bottom_nav_flow.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import javax.inject.Inject
import javax.inject.Named

class BottomNavFlowFragment : FlowFragment(),
    BottomNavigationView.OnNavigationItemSelectedListener {

    override val containerId = R.id.bottom_nav_flow_container

    override val layoutRes = R.layout.fragment_bottom_nav_flow

    private var currentSelectedTabId: Int? = null

    override val navigator: Navigator by lazy {
        object : SupportAppNavigator(requireActivity(), childFragmentManager, containerId) {
            override fun applyCommands(commands: Array<out Command>) {
                super.applyCommands(commands)
                updateBottomNav()
            }
        }
    }

    @Inject
    @Named(FLOW)
    lateinit var router: Router

    private val navigationMap = mapOf(
        R.id.menu_fragment to Screens.Menu,
        R.id.orders_fragment to Screens.Orders,
        R.id.cart_fragment to Screens.Cart,
        R.id.profile_fragment to Screens.Profile
    )

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (childFragmentManager.fragments.isEmpty()) {
            navigator.setLaunchScreen(Screens.Menu)
        }
        initViews()
    }


    private fun initViews() {
        showAppBar()
        with(bottom_nav_view) {
            setOnNavigationItemSelectedListener(this@BottomNavFlowFragment)
            setOnNavigationItemReselectedListener { /** (ﾉ◕ヮ◕)ﾉ*:･ﾟ✧ FIXED ✧ */ }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        navigationMap[item.itemId]?.let {
            router.replaceScreen(it)
            return true
        }
        return false
    }

    private fun updateBottomNav() {
        /**
         * without executePendingTransactions currentFragment is previousFragment
         * */
        childFragmentManager.executePendingTransactions()

        getCurrentFragmentId()?.let { currentFragmentId ->
            if (currentSelectedTabId != currentFragmentId) {
                currentSelectedTabId = currentFragmentId
                bottom_nav_view.selectedItemId = currentFragmentId
            }
        }
    }

    private fun getCurrentFragmentId(): Int? {
        return when (currentFragment) {
            is MenuFragment -> R.id.menu_fragment
            is OrdersFragment -> R.id.orders_fragment
            is CartFragment -> R.id.cart_fragment
            is ProfileFragment -> R.id.profile_fragment
            else -> null
        }
    }

    override fun onBackPressed() {
        currentFragment?.onBackPressed() ?: super.onBackPressed()
    }

}