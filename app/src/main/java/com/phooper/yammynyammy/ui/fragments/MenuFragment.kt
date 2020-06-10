package com.phooper.yammynyammy.ui.fragments

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.ui.adapters.ViewPagerFragmentAdapter
import com.phooper.yammynyammy.utils.Constants.Companion.CHOCOLATE_CATEGORY
import com.phooper.yammynyammy.utils.Constants.Companion.COOKIES_CATEGORY
import com.phooper.yammynyammy.utils.Constants.Companion.CUPCAKES_CATEGORY
import com.phooper.yammynyammy.utils.Constants.Companion.ICE_CREAM_CATEGORY
import kotlinx.android.synthetic.main.fragment_menu.*

class MenuFragment : BaseFragment() {
    override val layoutRes = R.layout.fragment_menu
    private lateinit var navController: NavController

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navController = findNavController()
        initViews()
    }

    private fun initViews() {
        view_pager.adapter = ViewPagerFragmentAdapter(this)
        TabLayoutMediator(tab_layout, view_pager) { tab, position ->
            tab.text = when (position + 1) {
                ICE_CREAM_CATEGORY -> getString(R.string.ice_cream)
                CUPCAKES_CATEGORY -> getString(R.string.cupcakes)
                COOKIES_CATEGORY -> getString(R.string.cookies)
                CHOCOLATE_CATEGORY -> getString(R.string.chocolate)
                else -> null
            }
        }.attach()

    }
}