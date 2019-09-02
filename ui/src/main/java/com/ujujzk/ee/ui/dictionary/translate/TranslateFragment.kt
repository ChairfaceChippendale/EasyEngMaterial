package com.ujujzk.ee.ui.dictionary.translate

import androidx.fragment.app.Fragment
import com.ujujzk.ee.ui.di.KOIN_NAV_DIC
import com.ujujzk.ee.ui.navigation.BackButtonListener
import com.ujujzk.ee.ui.navigation.FragmentScreen
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named
import ru.terrakok.cicerone.Router

class TranslateFragment : Fragment(), BackButtonListener {

    private val router: Router by inject(named(KOIN_NAV_DIC))

    override fun onBackPressed(): Boolean {
        router.exit()
        return true
    }

    class Screen : FragmentScreen({ TranslateFragment() })
}