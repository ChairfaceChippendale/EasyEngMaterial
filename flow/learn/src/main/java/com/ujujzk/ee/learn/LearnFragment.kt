package com.ujujzk.ee.learn

import com.ujujzk.ee.learn.databinding.FragmentLearnBinding
import com.ujujzk.ee.presentation.base.BaseFragment
import com.ujujzk.ee.presentation.base.InitViews
import com.ujujzk.ee.presentation.base.VMObserver
import com.ujujzk.ee.presentation.base.viewBinding
import com.ujujzk.ee.presentation.navigation.BackButtonListener
import com.ujujzk.ee.presentation.navigation.FragmentScreen
import com.ujujzk.ee.presentation.navigation.switchnav.NavBarOwner


class LearnFragment:
    BaseFragment<FragmentLearnBinding, LearnViewModel>(
        R.layout.fragment_learn,
        LearnViewModel::class
    ),
    BackButtonListener {

    override val binding by viewBinding(FragmentLearnBinding::bind)

    override val observer: VMObserver<LearnViewModel> = { /*no-op*/ }

    override val initViews: InitViews<FragmentLearnBinding> = { /*no-op*/ }

    override fun onResume() {
        super.onResume()
        //LearnFragment-VocabularyParent-MainParent
        (parentFragment?.parentFragment as? NavBarOwner)?.showNavBar()
    }
}