package com.phooper.yammynyammy.ui.global.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.phooper.yammynyammy.ui.product_list.ProductListFragment
import com.phooper.yammynyammy.utils.Constants.Companion.ARG_OBJECT
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

class ViewPagerFragmentAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment =
        ProductListFragment.create(position + 1)
}