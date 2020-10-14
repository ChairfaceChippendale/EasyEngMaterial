package com.ujujzk.ee.vocabulary

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ujujzk.ee.presentation.R
import com.ujujzk.ee.presentation.di.KOIN_NAV_VOC_CICERONE
import com.ujujzk.ee.presentation.di.VOCABULARY_TAG_TAB
import com.ujujzk.ee.presentation.navigation.FlowFragment
import com.ujujzk.ee.presentation.navigation.ScreenFactory
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator


class VocabularyParent: FlowFragment(VOCABULARY_TAG_TAB) {

    private val cicerone by inject<Cicerone<Router>>(named(KOIN_NAV_VOC_CICERONE))
    private lateinit var navigator: SupportAppNavigator
    private val screens: ScreenFactory by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_parent, container, false)
        root.setBackgroundColor(Color.GREEN)
        return root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigator = SupportAppNavigator(activity, childFragmentManager, R.id.container)
    }

    override fun onResume() {
        super.onResume()
        cicerone.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        cicerone.navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (childFragmentManager.findFragmentById(R.id.container) == null) {
            cicerone.router.replaceScreen(screens.store())
        }
    }
}