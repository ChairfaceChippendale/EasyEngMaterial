package com.ujujzk.ee.presentation.navigation.switchnav

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import com.ujujzk.ee.presentation.navigation.FlowFragment
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.commands.Back
import ru.terrakok.cicerone.commands.Command

/**
 * Custom [Navigator], designed to work with the Bottom navigation.
 * Allows to save nested navigation flows in the tabs.
 */
class SwitchNavigator(
    private val activity: FragmentActivity,
    private val fragmentManager: FragmentManager,
    private val containerId: Int
): Navigator {

    override fun applyCommands(commands: Array<out Command>?) {
        commands?.let {
            for (command in commands) applyCommand(command)
        }
    }

    private fun applyCommand(command: Command) {
        when (command) {
            is Back -> activity.finish()
            is SwitchFragment -> changeTab(command.fr)
        }
    }

    private fun changeTab(targetFragment: FlowFragment) {
        with(fragmentManager.beginTransaction()) {

            fragmentManager.fragments.filter{ it != targetFragment }.forEach {
                hide(it)
                //since hide doesn't trigger onPause, we use this instead to let the fragment know it is not visible
                //equivalent to `it.userVisibleHint = false`
                setMaxLifecycle(it, Lifecycle.State.STARTED)
            }

            targetFragment.let {
                if (it.isAdded) {
                    show(it)
                } else {
                    add(containerId, it, it.tabTag)
                }
                //equivalent to `it.userVisibleHint = true`
                setMaxLifecycle(it, Lifecycle.State.RESUMED)
            }
            commit()
        }
    }

    class SwitchFragment(val fr: FlowFragment) : Command
}