package com.phooper.yammynyammy.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.R.string.hello
import com.phooper.yammynyammy.viewmodels.LoginViewModel
import kotlinx.android.synthetic.main.fragment_phone_verification.*

class PhoneVerificationFragment : BaseFragment() {

    private val viewModel by activityViewModels<LoginViewModel>()
    private lateinit var navController: NavController
    override val layoutRes = R.layout.fragment_phone_verification

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navController = findNavController()
        initViews()
    }

    @SuppressLint("SetTextI18n")
    private fun initViews() {
        close_btn.setOnClickListener {
            navController.navigateUp()
        }

        viewModel.username.observe(viewLifecycleOwner, Observer {
            hello_label.text = "${getString(hello)} $it!"
        })
    }

    companion object
}