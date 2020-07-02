package com.phooper.yammynyammy.ui.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.viewmodels.MainContainerViewModel
import kotlinx.android.synthetic.main.activity_main_container.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainContainerActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener {

    private val viewModel by viewModel<MainContainerViewModel>()

    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var navController: NavController

    private val topLevelDestinations = setOf(
        R.id.menu_fragment,
        R.id.orders_fragment,
        R.id.profile_fragment,
        R.id.cart_fragment
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_container)
        setSupportActionBar(toolbar)
        setupNavigation()
        initViews()
    }

    private fun setupNavigation() {
        navController = findNavController(R.id.main_nav_host_fragment)

        appBarConfiguration =
            AppBarConfiguration(topLevelDestinations)

        setupActionBarWithNavController(navController, appBarConfiguration)

        findViewById<BottomNavigationView>(R.id.bottom_nav_view)
            .setupWithNavController(navController)

        findViewById<BottomNavigationView>(R.id.bottom_nav_view)
            .setOnNavigationItemSelectedListener(this@MainContainerActivity)
    }

    private fun initViews() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            toggleCartIconVisibility(destination.id)
            toggleBottomNavVisibility(destination.id)
            toolbar_layout?.elevation =
                if (destination.id == R.id.menu_fragment) 0f else resources.getDimension(R.dimen.app_bar_elevation)
        }
        viewModel.cartItemCount.observe(this, Observer {
            if (it == 0) {
                bottom_nav_view.removeBadge(R.id.cart_fragment)
            } else {
                bottom_nav_view.getOrCreateBadge(R.id.cart_fragment).apply {
                    maxCharacterCount = 3
                    number = it
                }
            }
        })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (navController.currentDestination?.id != item.itemId)
            return item.onNavDestinationSelected(navController)
        return false
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_app_bar_menu, menu)
        toggleCartIconVisibility(navController.currentDestination?.id)
        return super.onCreateOptionsMenu(menu)
    }

    private fun toggleCartIconVisibility(destId: Int?) {
        toolbar.menu.findItem(R.id.cart_fragment)?.isVisible =
            (!topLevelDestinations.contains(destId))
    }

    private fun toggleBottomNavVisibility(destId: Int?) {
        bottom_nav_view?.isVisible =
            (topLevelDestinations.contains(destId))
    }
}
