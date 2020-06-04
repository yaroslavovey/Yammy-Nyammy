package com.phooper.yammynyammy.ui.fragments

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.utils.setHideLayoutErrorOnTextChangedListener
import com.phooper.yammynyammy.viewmodels.LoginViewModel
import kotlinx.android.synthetic.main.fragment_name_phone_verification.*

class NamePhoneVerificationFragment : BaseFragment() {
    override val layoutRes = R.layout.fragment_name_phone_verification
    private lateinit var navController: NavController
    private val viewModel by activityViewModels<LoginViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navController = findNavController()
        initViews()
    }

    private fun initViews() {
        phone_number_input.setHideLayoutErrorOnTextChangedListener(phone_number_input_layout)
        name_input.setHideLayoutErrorOnTextChangedListener(name_input_layout)

        close_btn.setOnClickListener {
            navController.navigateUp()
        }

        viewModel.username.observe(viewLifecycleOwner, Observer {
            name_input.setText(it)
        })

        sign_in_btn.setOnClickListener {
            addUserData()
        }

        phone_number_input.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                addUserData()
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun addUserData() {
        if (areSomeInputsEmpty()) {
            showFillFieldsError()
            return
        }
        viewModel.handleAddUserData(name_input.text.toString(), phone_number_input.text.toString())
    }

    private fun areSomeInputsEmpty() =
        name_input.text.toString().isEmpty() ||
                phone_number_input.text.toString().isEmpty()

    private fun showFillFieldsError() {
        if (name_input.text.toString().isEmpty())
            name_input_layout.error =
                getString(R.string.fill_name)

        if (phone_number_input.text.toString().isEmpty())
            phone_number_input_layout.error =
                getString(R.string.fill_phone)
    }
}