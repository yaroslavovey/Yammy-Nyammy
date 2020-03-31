package com.phooper.yammynyammy.views.fragments

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.phooper.yammynyammy.R
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterViaGoogleFragment : BaseFragment() {
    override val layoutRes = R.layout.fragment_register_via_google
    private lateinit var navController: NavController

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        navController = findNavController()

        close_btn.setOnClickListener {
            navController.navigateUp()
        }
    }

}