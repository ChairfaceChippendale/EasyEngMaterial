package com.ujujzk.ee.ui.dictionary.translate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.ujujzk.ee.ui.R
import com.ujujzk.ee.ui.di.KOIN_NAV_DIC_ROUTER
import com.ujujzk.ee.ui.dictionary.catalog.CatalogFragment
import com.ujujzk.ee.ui.navigation.BackButtonListener
import com.ujujzk.ee.ui.navigation.FragmentScreen
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named
import ru.terrakok.cicerone.Router

class TranslateFragment : Fragment(), BackButtonListener {

    private val router: Router by inject(named(KOIN_NAV_DIC_ROUTER))

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_translate, container, false)

        root.findViewById<Button>(R.id.catalogueBtn).apply {
            setOnClickListener {
                router.navigateTo(CatalogFragment.Screen())
            }
        }

        return root
    }

    override fun onBackPressed(): Boolean {
        router.exit()
        return true
    }

    class Screen : FragmentScreen({ TranslateFragment() })
}