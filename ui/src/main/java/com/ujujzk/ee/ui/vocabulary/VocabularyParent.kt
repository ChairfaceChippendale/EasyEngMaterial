package com.ujujzk.ee.ui.vocabulary

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ujujzk.ee.ui.R
import com.ujujzk.ee.ui.di.KOIN_NAV_VOC_CICERONE
import com.ujujzk.ee.ui.navigation.TabFragment
import com.ujujzk.ee.ui.vocabulary.packs.PacksFragment
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class VocabularyParent : TabFragment(VOC_TAG_TAB) {

    private val cicerone by inject<Cicerone<Router>>(named(KOIN_NAV_VOC_CICERONE))
    private lateinit var navigator: SupportAppNavigator

    companion object {
        const val VOC_TAG_TAB = "VocabularyFlow"
        fun inst(): VocabularyParent = VocabularyParent()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_parent, container, false)
        val title = root.findViewById<TextView>(R.id.title)
        title.text = "Vocabulary"
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (childFragmentManager.findFragmentById(R.id.container) == null) {
            cicerone.router.replaceScreen(PacksFragment.Screen())
        }
    }
}