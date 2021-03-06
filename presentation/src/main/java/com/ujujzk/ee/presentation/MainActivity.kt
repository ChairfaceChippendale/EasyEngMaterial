package com.ujujzk.ee.presentation

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.ujujzk.ee.presentation.di.KOIN_NAV_APP_CICERONE
import com.ujujzk.ee.presentation.navigation.BackButtonListener
import com.ujujzk.ee.presentation.navigation.ScreenFactory
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named


class MainActivity: AppCompatActivity(R.layout.activity_main) {

    private val cicerone : Cicerone<Router> by inject(named(KOIN_NAV_APP_CICERONE))
    private val appNavigator by lazy { AppNavigator(this, R.id.container, supportFragmentManager) }
    private val screens: ScreenFactory by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            cicerone.router.newRootScreen(screens.mainParent())
        }

        setupEdgeToEdge()
    }

    private fun setupEdgeToEdge(){
        window.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                decorView.systemUiVisibility = decorView.systemUiVisibility or
                        View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR or
                        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                statusBarColor = ContextCompat.getColor(context, R.color.colorStatusBar)
            }
            navigationBarColor = ContextCompat.getColor(context, R.color.colorNavBar)
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        cicerone.getNavigatorHolder().setNavigator(appNavigator)
    }

    override fun onPause() {
        cicerone.getNavigatorHolder().removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.filter{ it.isVisible }.forEach {
            if (!(it as BackButtonListener).onBackPressed())
                finish()
        }
    }
}