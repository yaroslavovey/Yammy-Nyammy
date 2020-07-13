package com.phooper.yammynyammy.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.utils.showMessageAboveBottomNav
import com.phooper.yammynyammy.viewmodels.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class ProfileFragment : BaseFragment() {

    override val layoutRes = R.layout.fragment_profile

    private lateinit var navController: NavController

    private val viewModel by viewModel<ProfileViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navController = findNavController()
        initViews()
    }

    private fun initViews() {
        viewModel.state.observe(viewLifecycleOwner, Observer {
            it?.let { viewState ->
                when (viewState) {
                    ProfileViewModel.ViewState.DEFAULT -> {
                        progress_bar.visibility = View.GONE
                    }
                    ProfileViewModel.ViewState.LOADING -> {
                        progress_bar.visibility = View.VISIBLE
                    }
                }
            }
        })

        viewModel.email.observe(viewLifecycleOwner, Observer {
            email.text = it
        })

        viewModel.event.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { event ->
                when (event) {
                    ProfileViewModel.ViewEvent.ERROR -> {
                        showMessageAboveBottomNav(R.string.error)
                    }
                }
            }
        })

        sign_out_btn.setOnClickListener {
            viewModel.signOut()
        }

        my_addresses_layout.setOnClickListener {
            navController.navigate(
                ProfileFragmentDirections.actionProfileFragmentToMyAddressesFragment(
                    choosingAddressForDelivery = false
                )
            )
        }

        change_password_layout.setOnClickListener {
            navController.navigate(ProfileFragmentDirections.actionProfileFragmentToUpdatePasswordFragment())
        }

        personal_data_layout.setOnClickListener {
            navController.navigate(ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment())
        }
    }
}