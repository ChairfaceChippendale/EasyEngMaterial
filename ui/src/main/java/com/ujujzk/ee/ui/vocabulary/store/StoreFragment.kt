package com.ujujzk.ee.ui.vocabulary.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.ujujzk.ee.ui.R
import com.ujujzk.ee.ui.di.KOIN_NAV_VOC_ROUTER
import com.ujujzk.ee.ui.navigation.BackButtonListener
import com.ujujzk.ee.ui.navigation.FragmentScreen
import com.ujujzk.ee.ui.vocabulary.learn.LearnFragment
import com.ujujzk.ee.ui.vocabulary.pack.PackFragment
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named
import ru.terrakok.cicerone.Router

class StoreFragment:  Fragment(), BackButtonListener {

    private val router: Router by inject(named(KOIN_NAV_VOC_ROUTER))

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_store, container, false)

        root.findViewById<Button>(R.id.learnBtn).apply {
            setOnClickListener {
                router.navigateTo(LearnFragment.Screen())
            }
        }

        root.findViewById<Button>(R.id.packBtn).apply {
            setOnClickListener {
                router.navigateTo(PackFragment.Screen())
            }
        }

        return root
    }

    override fun onBackPressed(): Boolean {
        router.exit()
        return true
    }

    class Screen : FragmentScreen({ StoreFragment() })
}