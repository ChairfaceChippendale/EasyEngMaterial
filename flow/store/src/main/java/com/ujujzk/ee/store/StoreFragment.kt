package com.ujujzk.ee.store

import com.ujujzk.ee.store.databinding.FragmentStoreBinding
import com.ujujzk.ee.presentation.base.BaseFragment
import com.ujujzk.ee.presentation.base.InitViews
import com.ujujzk.ee.presentation.base.VMObserver
import com.ujujzk.ee.presentation.base.viewBinding
import com.ujujzk.ee.presentation.navigation.BackButtonListener
import com.ujujzk.ee.presentation.navigation.switchnav.NavBarOwner
import com.ujujzk.ee.presentation.tools.onClick


class StoreFragment :
    BaseFragment<FragmentStoreBinding, StoreViewModel>(
        R.layout.fragment_store,
        StoreViewModel::class
    ),
    BackButtonListener {

    override val binding by viewBinding(FragmentStoreBinding::bind)

    override val observer: VMObserver<StoreViewModel> = { /*no-op*/ }

    override val initViews: InitViews<FragmentStoreBinding> = {
        catalogBtn.onClick { viewModel.onCatalogClick() }
        learnBtn.onClick { viewModel.onLearnClick() }
        packBtn.onClick { viewModel.onEditPackClick() }
    }

    override fun onResume() {
        super.onResume()
        //StoreFragment-VocabularyParent-MainParent
        (parentFragment?.parentFragment as? NavBarOwner)?.showNavBar()
        /*
       todo test this
       var f: Fragment? = this
       while (f != null || f is NavBarOwner){
           f = f.parentFragment
       }
       (f as? NavBarOwner)?.showNavBar()
       */
    }
}