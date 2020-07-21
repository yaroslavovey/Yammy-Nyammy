package com.phooper.yammynyammy.ui.global

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.phooper.yammynyammy.utils.setAppBarTitle
import dagger.android.support.AndroidSupportInjection
import moxy.MvpAppCompatFragment


abstract class BaseFragment : MvpAppCompatFragment() {

    abstract val layoutRes: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutRes, container, false)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    open fun onBackPressed() {}
}
