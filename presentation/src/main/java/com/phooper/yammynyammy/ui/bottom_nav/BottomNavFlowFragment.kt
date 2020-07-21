package com.phooper.yammynyammy.ui.bottom_nav

import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.Screens
import com.phooper.yammynyammy.ui.global.FlowFragment
import com.phooper.yammynyammy.utils.Constants.Companion.FLOW
import com.phooper.yammynyammy.utils.setLaunchScreen
import com.phooper.yammynyammy.utils.showAppBar
import kotlinx.android.synthetic.main.fragment_bottom_nav_flow.*
import ru.terrakok.cicerone.Router
import javax.inject.Inject
import javax.inject.Named

class BottomNavFlowFragment : FlowFragment(),
    BottomNavigationView.OnNavigationItemSelectedListener {

    override val containerId = R.id.bottom_nav_flow_container

    override val layoutRes = R.layout.fragment_bottom_nav_flow

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

    override fun onBackPressed() {
        currentFragment?.onBackPressed() ?: super.onBackPressed()
    }

}