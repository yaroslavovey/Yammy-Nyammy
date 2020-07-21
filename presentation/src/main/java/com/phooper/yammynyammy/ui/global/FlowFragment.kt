package com.phooper.yammynyammy.ui.global

import com.phooper.yammynyammy.ui.global.BaseFragment
import com.phooper.yammynyammy.utils.Constants
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject
import javax.inject.Named

abstract class FlowFragment : BaseFragment() {

    abstract val containerId: Int

    @Inject
    @Named(Constants.FLOW)
    lateinit var navigatorHolder: NavigatorHolder

    protected val currentFragment
        get() = childFragmentManager.findFragmentById(containerId) as? BaseFragment

    protected val navigator: Navigator by lazy {
        SupportAppNavigator(requireActivity(), childFragmentManager, containerId)
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

}