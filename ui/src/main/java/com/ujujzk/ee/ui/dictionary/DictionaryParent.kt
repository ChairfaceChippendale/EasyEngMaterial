package com.ujujzk.ee.ui.dictionary

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ujujzk.ee.ui.R
import com.ujujzk.ee.ui.di.KOIN_NAV_DIC
import com.ujujzk.ee.ui.dictionary.translate.TranslateFragment
import com.ujujzk.ee.ui.navigation.TabFragment
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class DictionaryParent: TabFragment(TAG_TAB) {

    private val cicerone  by inject<Cicerone<Router>>(named(KOIN_NAV_DIC))
    private val navigator by lazy { SupportAppNavigator(activity, childFragmentManager, R.id.container) }

    companion object {
        const val TAG_TAB = "DictionaryFlow"
        fun inst(): DictionaryParent = DictionaryParent()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_parent, container, false)
        val title = root.findViewById<TextView>(R.id.title)
        title.text = "Dictionary"
        root.setBackgroundColor(Color.GRAY)
        return root
    }

    override fun onResume() {
        super.onResume()
        cicerone.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        cicerone.navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (childFragmentManager.findFragmentById(R.id.container) == null) {
            cicerone.router.replaceScreen(TranslateFragment.Screen())
        }
    }
}