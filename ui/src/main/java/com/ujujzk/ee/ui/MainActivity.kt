package com.ujujzk.ee.ui

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.ujujzk.ee.ui.di.KOIN_NAV_MAIN_CICERONE
import com.ujujzk.ee.ui.di.KOIN_NAV_MAIN_ROUTER
import com.ujujzk.ee.ui.dictionary.DictionaryParent
import com.ujujzk.ee.ui.dictionary.DictionaryParent.Companion.DIC_TAG_TAB
import com.ujujzk.ee.ui.dictionary.translate.TranslateFragment
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

    private val mainCicerone : Cicerone<BaseRouter> by inject(named(KOIN_NAV_MAIN_CICERONE))
    private val tabRouter: SwitchRouter by inject(named(KOIN_NAV_MAIN_ROUTER))
    private val tabNavigator by lazy {  SwitchNavigator(this, supportFragmentManager, R.id.container) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            tabRouter.switchFragment(dicFlow)
        }

        findViewById<Button>(R.id.dic).apply {
            setOnClickListener {
                tabRouter.switchFragment(dicFlow)
            }
        }

        findViewById<Button>(R.id.voc).apply {
            setOnClickListener {
                tabRouter.switchFragment(vocFlow)
            }
        }

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