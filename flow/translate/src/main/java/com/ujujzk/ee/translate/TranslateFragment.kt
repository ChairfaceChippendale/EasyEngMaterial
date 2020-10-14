package com.ujujzk.ee.translate

import com.ujujzk.ee.translate.databinding.FragmentTranslateBinding
import com.ujujzk.ee.presentation.base.BaseFragment
import com.ujujzk.ee.presentation.base.InitViews
import com.ujujzk.ee.presentation.base.VMObserver
import com.ujujzk.ee.presentation.base.viewBinding
import com.ujujzk.ee.presentation.navigation.BackButtonListener
import com.ujujzk.ee.presentation.navigation.switchnav.NavBarOwner
import com.ujujzk.ee.presentation.tools.onClick


class TranslateFragment :
    BaseFragment<FragmentTranslateBinding, TranslateViewModel>(
        R.layout.fragment_translate,
        TranslateViewModel::class
    ),
    BackButtonListener {

    override val binding by viewBinding(FragmentTranslateBinding::bind)

    override val observer: VMObserver<TranslateViewModel> = {/*no-op*/}

    override val initViews: InitViews<FragmentTranslateBinding> = {
        btnCatalogue.onClick { viewModel.onCatalogClick() }
    }

    override fun onResume() {
        super.onResume()
        //TranslateFragment-DictionaryParent-MainParent
        (parentFragment?.parentFragment as? NavBarOwner)?.showNavBar()
    }
}