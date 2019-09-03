package com.ujujzk.ee.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ujujzk.ee.ui.di.KOIN_NAV_MAIN
import com.ujujzk.ee.ui.dictionary.DictionaryParent
import com.ujujzk.ee.ui.dictionary.DictionaryParent.Companion.DIC_TAG_TAB
import com.ujujzk.ee.ui.navigation.BackButtonListener
import com.ujujzk.ee.ui.navigation.SwitchNavigator
import com.ujujzk.ee.ui.navigation.SwitchRouter
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

    private val localCicerone : Cicerone<BaseRouter> by inject(named(KOIN_NAV_MAIN))
    private val router: SwitchRouter by inject(named(KOIN_NAV_MAIN))
    private val navigator by lazy {  SwitchNavigator(this, supportFragmentManager, R.id.container) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            router.switchFragment(dicFlow)
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        localCicerone.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        localCicerone.navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.filter{ it.isVisible }.forEach {
            if (!(it as BackButtonListener).onBackPressed())
                finish()
        }
    }
}