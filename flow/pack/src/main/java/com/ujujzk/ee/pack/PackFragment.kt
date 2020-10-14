package com.ujujzk.ee.pack

import com.ujujzk.ee.pack.databinding.FragmentPackBinding
import com.ujujzk.ee.presentation.base.BaseFragment
import com.ujujzk.ee.presentation.base.InitViews
import com.ujujzk.ee.presentation.base.VMObserver
import com.ujujzk.ee.presentation.base.viewBinding
import com.ujujzk.ee.presentation.navigation.BackButtonListener
import com.ujujzk.ee.presentation.navigation.switchnav.NavBarOwner


class PackFragment :
    BaseFragment<FragmentPackBinding, PackViewModel>(
        R.layout.fragment_pack,
        PackViewModel::class
    ),
    BackButtonListener {

    override val binding by viewBinding(FragmentPackBinding::bind)

    override val observer: VMObserver<PackViewModel> = { /*no-op*/ }

    override val initViews: InitViews<FragmentPackBinding> = { /*no-op*/ }

    override fun onResume() {
        super.onResume()
        //PackFragment-VocabularyParent-MainParent
        (parentFragment?.parentFragment as? NavBarOwner)?.showNavBar()
    }
}