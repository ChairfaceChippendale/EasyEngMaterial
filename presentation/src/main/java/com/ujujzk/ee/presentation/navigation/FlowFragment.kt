package com.ujujzk.ee.presentation.navigation

import androidx.fragment.app.Fragment
import com.ujujzk.ee.presentation.R

abstract class FlowFragment (val tabTag: String) : Fragment(), BackButtonListener {

    override fun onBackPressed(): Boolean {
        return if (isAdded) {
            val childFragment = childFragmentManager.findFragmentById(R.id.container)
            childFragment != null && childFragment is BackButtonListener && childFragment.onBackPressed()
        } else false
    }
}