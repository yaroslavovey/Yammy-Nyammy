package com.phooper.yammynyammy.ui.profile

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.ui.global.BaseFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

class ProfileFragment : BaseFragment() {

    override val layoutRes = R.layout.fragment_profile


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
//        viewModel.state.observe(viewLifecycleOwner, Observer {
//            it?.let { viewState ->
//                when (viewState) {
//                    ProfileViewModel.ViewState.DEFAULT -> {
//                        progress_bar.visibility = View.GONE
//                    }
//                    ProfileViewModel.ViewState.LOADING -> {
//                        progress_bar.visibility = View.VISIBLE
//                    }
//                }
//            }
//        })
//
//        viewModel.email.observe(viewLifecycleOwner, Observer {
//            email.text = it
//        })
//
//        viewModel.event.observe(viewLifecycleOwner, Observer {
//            it.getContentIfNotHandled()?.let { event ->
//                when (event) {
//                    ProfileViewModel.ViewEvent.ERROR -> {
//                        showMessageAboveBottomNav(R.string.error)
//                    }
//                }
//            }
//        })
//
//        sign_out_btn.setOnClickListener {
//            viewModel.signOut()
//        }
//
//        my_addresses_layout.setOnClickListener {
//            navController.navigate(
//                ProfileFragmentDirections.actionProfileFragmentToMyAddressesFragment(
//                    choosingAddressForDelivery = false
//                )
//            )
//        }
//
//        change_password_layout.setOnClickListener {
//            navController.navigate(ProfileFragmentDirections.actionProfileFragmentToUpdatePasswordFragment())
//        }
//
//        personal_data_layout.setOnClickListener {
//            navController.navigate(ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment())
//        }
    }
}