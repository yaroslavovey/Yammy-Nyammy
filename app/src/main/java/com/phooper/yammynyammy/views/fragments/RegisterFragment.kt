package com.phooper.yammynyammy.views.fragments

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.utils.hideKeyboard
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : BaseFragment() {
    override val layoutRes = R.layout.fragment_register
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

        password_repeat_input.setOnEditorActionListener { view, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                view.apply {
                    clearFocus()
                    this.context.hideKeyboard(this)
                }
                return@setOnEditorActionListener true
            }
            false
        }
    }

}
