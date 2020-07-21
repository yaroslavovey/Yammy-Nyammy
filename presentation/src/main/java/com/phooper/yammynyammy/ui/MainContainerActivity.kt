package com.phooper.yammynyammy.ui

import android.os.Bundle
import android.view.MenuItem
import com.ph00.domain.usecases.GetIsUserSignedInUseCase
import com.phooper.yammynyammy.R
import com.phooper.yammynyammy.Screens
import com.phooper.yammynyammy.ui.global.BaseFragment
import com.phooper.yammynyammy.utils.Constants.Companion.GLOBAL
import com.phooper.yammynyammy.utils.SystemMessageNotifier
import com.phooper.yammynyammy.utils.setLaunchScreen
import com.phooper.yammynyammy.utils.showMessageKtx
import dagger.android.AndroidInjection
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main_container.*
import moxy.MvpAppCompatActivity
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject
import javax.inject.Named

class MainContainerActivity : MvpAppCompatActivity(R.layout.activity_main_container) {

    @Inject
    @Named(GLOBAL)
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var systemMessageNotifier: SystemMessageNotifier

    @Inject
    lateinit var signedInUseCase: GetIsUserSignedInUseCase

    @Inject
    @Named(GLOBAL)
    lateinit var router: Router

    private val currentFragment: BaseFragment?
        get() = supportFragmentManager.findFragmentById(R.id.main_container) as? BaseFragment

    private val navigator: Navigator =
        SupportAppNavigator(this, supportFragmentManager, R.id.main_container)

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        subscribeOnAuthState()
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)

        if (supportFragmentManager.fragments.isEmpty()) {
            navigator.setLaunchScreen(Screens.BottomNavFlow)
        }
        subscribeOnSystemMessages()
        setSupportActionBar(toolbar)
        supportActionBar?.hide()
        initViews()
    }

    override fun onBackPressed() {
        currentFragment?.onBackPressed() ?: super.onBackPressed()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    private fun subscribeOnAuthState() {
        signedInUseCase
            .execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { isLoggedIn -> router.navigateTo(if (isLoggedIn) Screens.BottomNavFlow else Screens.LoginFlow) }
            .subscribe()
    }

    private fun subscribeOnSystemMessages() {
        systemMessageNotifier
            .notifier
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { showMessageKtx(it) }
            .subscribe()
    }

    private fun initViews() {


//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            toggleBottomNavVisibility(destination.id)

//        }
//
//        bottom_nav_view.getOrCreateBadge(R.id.cart_fragment).maxCharacterCount = 3
//
//        viewModel.cartItemCount.observe(this, Observer {
//            if (it == 0) {
//                bottom_nav_view.removeBadge(R.id.cart_fragment)
//            } else {
//                bottom_nav_view.getOrCreateBadge(R.id.cart_fragment).number = it
//            }
//        })
//
//        viewModel.event.observe(this, Observer {
//            it?.getContentIfNotHandled()?.let { event ->
//                when (event) {
//                    MainContainerViewModel.ViewEvent.ADDED_TO_CART -> {
//                        Snackbar.make(
//                            findViewById(R.id.coordinator_layout),
//                            R.string.added_to_cart,
//                            Snackbar.LENGTH_SHORT
//                        )
//                            .setAction(R.string.go_to_cart) { navController.navigate(R.id.cart_fragment) }
//                            .show()
//                    }
//                    MainContainerViewModel.ViewEvent.NAVIGATE_TO_LOGIN_ACTIVITY -> {
//                        startActivity(Intent(this, LoginContainerActivity::class.java))
//                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
//                        finish()
//                    }
//                }
//            }
//        })
    }

    //
//
//    override fun onSupportNavigateUp(): Boolean {
//        return navController.navigateUp(appBarConfiguration)
//                || super.onSupportNavigateUp()
//    }

}
