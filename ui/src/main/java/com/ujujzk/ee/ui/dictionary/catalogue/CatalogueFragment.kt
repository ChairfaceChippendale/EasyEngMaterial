package com.ujujzk.ee.ui.dictionary.catalogue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.ujujzk.ee.ui.R
import com.ujujzk.ee.ui.di.KOIN_NAV_DIC_ROUTER
import com.ujujzk.ee.ui.navigation.BackButtonListener
import com.ujujzk.ee.ui.navigation.FragmentScreen
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named
import ru.terrakok.cicerone.Router

class CatalogueFragment : Fragment(), BackButtonListener {

    private val router: Router by inject(named(KOIN_NAV_DIC_ROUTER))

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_catalogue, container, false)

        return root
    }

    override fun onBackPressed(): Boolean {
        router.exit()
        return true
    }

    class Screen : FragmentScreen({ CatalogueFragment() })
}