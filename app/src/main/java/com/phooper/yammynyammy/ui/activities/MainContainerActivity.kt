package com.phooper.yammynyammy.ui.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.viewmodels.MainContainerViewModel
import kotlinx.android.synthetic.main.activity_main_container.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainContainerActivity : AppCompatActivity() {

    private val viewModel by viewModel<MainContainerViewModel>()
    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_container)
        initViews()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    private fun initViews() {
        navController = findNavController(R.id.main_nav_host_fragment)
        appBarConfiguration =
            AppBarConfiguration(
                setOf(
                    R.id.menu_fragment,
                    R.id.orders_fragment,
                    R.id.profile_fragment,
                    R.id.cart_fragment
                ), drawer_layout
            )
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
        findViewById<NavigationView>(R.id.nav_view).setupWithNavController(navController)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.cart_fragment) navController.navigate(R.id.cart_fragment)
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_app_bar_menu, menu);
        return super.onCreateOptionsMenu(menu)
    }
}
