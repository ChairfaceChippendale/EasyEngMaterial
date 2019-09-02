package com.ujujzk.ee.ui.navigation

import android.content.Context
import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

open class FragmentScreen (private val provider: (Unit) -> Fragment): SupportAppScreen() {
    final override fun getFragment() = provider.invoke(Unit)
    final override fun getActivityIntent(context: Context?) = null
}