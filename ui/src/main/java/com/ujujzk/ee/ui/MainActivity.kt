package com.ujujzk.ee.ui

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.ujujzk.ee.ui.di.KOIN_NAV_MAIN_CICERONE
import com.ujujzk.ee.ui.di.KOIN_NAV_MAIN_ROUTER
import com.ujujzk.ee.ui.dictionary.DictionaryParent
import com.ujujzk.ee.ui.dictionary.DictionaryParent.Companion.DIC_TAG_TAB
import com.ujujzk.ee.ui.navigation.BackButtonListener
import com.ujujzk.ee.ui.navigation.SwitchNavigator
import com.ujujzk.ee.ui.navigation.SwitchRouter
import com.ujujzk.ee.ui.tools.addSystemBottomPadding
import com.ujujzk.ee.ui.vocabulary.VocabularyParent
import com.ujujzk.ee.ui.vocabulary.VocabularyParent.Companion.VOC_TAG_TAB
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named
import ru.terrakok.cicerone.BaseRouter
import ru.terrakok.cicerone.Cicerone

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val dicFlow: DictionaryParent by lazy {
        supportFragmentManager.findFragmentByTag(DIC_TAG_TAB) as? DictionaryParent ?: DictionaryParent.inst()
    }
    private val vocFlow: VocabularyParent by lazy {
        supportFragmentManager.findFragmentByTag(VOC_TAG_TAB) as? VocabularyParent ?: VocabularyParent.inst()
    }

    private val mainCicerone : Cicerone<BaseRouter> by inject(named(KOIN_NAV_MAIN_CICERONE))
    private val tabRouter: SwitchRouter by inject(named(KOIN_NAV_MAIN_ROUTER))
    private val tabNavigator by lazy {  SwitchNavigator(this, supportFragmentManager, R.id.container) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            tabRouter.switchFragment(dicFlow)
        }

        setupEdgeToEdge()
        setupBottomMenu()

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
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

            statusBarColor = ContextCompat.getColor(context, R.color.colorStatusBar)
            navigationBarColor = ContextCompat.getColor(context, R.color.colorNavBar)
        }
    }

    private fun setupBottomMenu(){
        val menu = findViewById<AHBottomNavigation>(R.id.bottom_navigation)

        val dicTab = AHBottomNavigationItem(R.string.dictionary_title, R.drawable.ic_dictionary_black_24dp, R.color.dicTab)
        val vocTab = AHBottomNavigationItem(R.string.vocabulary_title, R.drawable.ic_vocabulary_black_24dp, R.color.vocTab)
        val grmTab = AHBottomNavigationItem(R.string.grammar_title, R.drawable.ic_grammar_black_24dp, R.color.gramTab)

        menu.addItem(dicTab)
        menu.addItem(vocTab)
        menu.addItem(grmTab)

        menu.disableItemAtPosition(2)

        menu.isColored = true
        menu.setCurrentItem(0, false)

        menu.setOnTabSelectedListener { position, _ ->
            when (position){
                0 -> tabRouter.switchFragment(dicFlow)
                1 -> tabRouter.switchFragment(vocFlow)
            }
            true
        }

        menu.addSystemBottomPadding()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        mainCicerone.navigatorHolder.setNavigator(tabNavigator)
    }

    override fun onPause() {
        mainCicerone.navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.filter{ it.isVisible }.forEach {
            if (!(it as BackButtonListener).onBackPressed())
                finish()
        }

    }
}