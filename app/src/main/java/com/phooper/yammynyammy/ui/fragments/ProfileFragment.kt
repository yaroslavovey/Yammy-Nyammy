package com.phooper.yammynyammy.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.ui.activities.LoginActivity
import com.phooper.yammynyammy.utils.showMessageAboveBottomNav
import com.phooper.yammynyammy.viewmodels.MainContainerViewModel
import com.phooper.yammynyammy.viewmodels.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment() {

    override val layoutRes = R.layout.fragment_profile

    private lateinit var navController: NavController

    private val viewModel by viewModel<ProfileViewModel>()

    private val sharedViewModel by sharedViewModel<MainContainerViewModel>()

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

        viewModel.userData.observe(viewLifecycleOwner, Observer {
            email.text = it.email
            username.text = it.name
            phone_number.text = it.phoneNum

        })

        viewModel.event.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { event ->
                when (event) {
                    ProfileViewModel.ViewEvent.ERROR -> {
                        requireActivity().showMessageAboveBottomNav(R.string.error)
                    }
                    //TODO Find better way
                    ProfileViewModel.ViewEvent.NAVIGATE_TO_LOGIN_ACTIVITY -> {
                        with(requireActivity()) {
                            startActivity(Intent(this, LoginActivity::class.java))
                            overridePendingTransition(
                                android.R.anim.fade_in,
                                android.R.anim.fade_out
                            )
                            finish()
                        }
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

        edit_btn.setOnClickListener {
            navController.navigate(ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment())
        }
    }
}