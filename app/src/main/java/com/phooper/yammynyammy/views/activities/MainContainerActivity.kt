package com.phooper.yammynyammy.views.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.viewmodels.MainContainerViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainContainerActivity : AppCompatActivity() {

    private val startViewModel by viewModel<MainContainerViewModel>()

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_container)
//        navController = findNavController(R.id.nav_host_fragment)
//        initViews()
    }

    private fun initViews() {
    }


}
