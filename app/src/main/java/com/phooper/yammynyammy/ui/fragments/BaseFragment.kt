package com.phooper.yammynyammy.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar


abstract class BaseFragment : Fragment() {
    abstract val layoutRes: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutRes, container, false)

    fun showMessage(msgRes: Int, contextView: View) {
        Snackbar.make(contextView, msgRes, Snackbar.LENGTH_SHORT).show()
    }
}
