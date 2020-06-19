package com.phooper.yammynyammy.ui.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.navigation.NavigationView
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.viewmodels.MainContainerViewModel
import kotlinx.android.synthetic.main.activity_main_container.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainContainerActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val viewModel by viewModel<MainContainerViewModel>()
    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_container)
        setSupportActionBar(toolbar)
        setupNavigation()
    }

    private fun setupNavigation() {
        navController = findNavController(R.id.main_nav_host_fragment).apply {
            addOnDestinationChangedListener { _, destination, _ ->
                nav_view.setCheckedItem(destination.id)
                toolbar_layout?.elevation =
                    if (destination.id == R.id.menu_fragment) 0f else resources.getDimension(R.dimen.app_bar_elevation)
            }
        }
        appBarConfiguration =
            AppBarConfiguration(
                setOf(
                    R.id.menu_fragment,
                    R.id.orders_fragment,
                    R.id.profile_fragment,
                    R.id.cart_fragment
                ), findViewById(R.id.drawer_layout)
            )
        setupActionBarWithNavController(navController, appBarConfiguration)
        findViewById<NavigationView>(R.id.nav_view).setNavigationItemSelectedListener(this@MainContainerActivity)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawer_layout.closeDrawer(GravityCompat.START)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_app_bar_menu, menu);
        return super.onCreateOptionsMenu(menu)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
