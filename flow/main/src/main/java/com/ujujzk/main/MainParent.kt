package com.ujujzk.main

import android.content.Context
import android.os.Bundle
import android.view.View
import com.github.terrakok.cicerone.BaseRouter
import com.github.terrakok.cicerone.Cicerone
import com.ujujzk.ee.presentation.base.BaseFragment
import com.ujujzk.ee.presentation.base.InitViews
import com.ujujzk.ee.presentation.base.VMObserver
import com.ujujzk.ee.presentation.base.viewBinding
import com.ujujzk.ee.presentation.di.*
import com.ujujzk.ee.presentation.navigation.BackButtonListener
import com.ujujzk.ee.presentation.navigation.FlowFragment
import com.ujujzk.ee.presentation.navigation.switchnav.NavBarOwner
import com.ujujzk.ee.presentation.navigation.switchnav.SwitchNavigator
import com.ujujzk.ee.presentation.tools.visible
import com.ujujzk.main.databinding.FragmentMainBinding
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named
import java.lang.IllegalArgumentException

class MainParent :
    BaseFragment<FragmentMainBinding, MainViewModel>(
        R.layout.fragment_main,
        MainViewModel::class
    ),
    BackButtonListener,
    NavBarOwner {

    companion object{
        const val KEY_NAVIGATION_VIEW_ID = "key_navigation_view_id"
    }

    private val cicerone: Cicerone<BaseRouter> by inject(named(KOIN_NAV_MAIN_CICERONE))
    private lateinit var tabNavigator : SwitchNavigator

    override val binding: FragmentMainBinding by viewBinding(FragmentMainBinding::bind)

    private val dictionaryFlow: FlowFragment by lazy {
        childFragmentManager.findFragmentByTag(DICTIONARY_TAG_TAB) as? FlowFragment
            ?: getKoin().get(named(PARENT_DICTIONARY))
    }
    private val vocabularyFlow: FlowFragment by lazy {
        childFragmentManager.findFragmentByTag(VOCABULARY_TAG_TAB) as? FlowFragment
            ?: getKoin().get(named(PARENT_VOCABULARY))
    }

//    private val grammarFlow: FlowFragment by lazy {
//        childFragmentManager.findFragmentByTag(GRAMMAR_TAG_TAB) as? FlowFragment
//            ?: getKoin().get(named(PARENT_GRAMMAR))
//    }


    override val observer: VMObserver<MainViewModel> = {/*NO-OP*/}

    override val initViews: InitViews<FragmentMainBinding> = {/*NO-OP*/}

    override fun onBackPressed(): Boolean {
        childFragmentManager.fragments.filter{ it.isVisible }.forEach {
            return if ((it as BackButtonListener).onBackPressed()) {
                true
            } else {
                viewModel.onBackPressed()
            }
        }
        return false
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        tabNavigator = SwitchNavigator(requireActivity(), childFragmentManager, R.id.container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBottomNavigation(savedInstanceState?.getInt(KEY_NAVIGATION_VIEW_ID) ?: R.id.navigation_dictionary)
    }

    private fun initBottomNavigation(navigationViewId: Int) {
        binding.bnMenu.setOnNavigationItemSelectedListener { //todo move to viewModel
            when (it.itemId) {
                R.id.navigation_dictionary -> {
                    viewModel.onDictionaryClick(dictionaryFlow)
                }
                R.id.navigation_vocabulary -> {
                    viewModel.onVocabularyClick(vocabularyFlow)
                }
                R.id.navigation_grammar -> {
//                    viewModel.onGrammarClick(grammarFlow)
                }
                else -> throw IllegalArgumentException("Unknown tab selected")
            }
            true
        }
        binding.bnMenu.selectedItemId = navigationViewId
    }

    override fun onResume() {
        super.onResume()
        cicerone.getNavigatorHolder().setNavigator(tabNavigator)
    }

    override fun onPause() {
        cicerone.getNavigatorHolder().removeNavigator()
        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(KEY_NAVIGATION_VIEW_ID, binding.bnMenu.selectedItemId)
        super.onSaveInstanceState(outState)
    }

    override fun hideNavBar() {
        binding.bnMenu.visible(false)
    }

    override fun showNavBar() {
        binding.bnMenu.visible(true)
    }
}