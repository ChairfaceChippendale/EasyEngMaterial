package com.ujujzk.ee.ui.dictionary.catalog

import android.os.Bundle
import com.ujujzk.ee.ui.R
import com.ujujzk.ee.ui.base.BaseFragment
import com.ujujzk.ee.ui.databinding.FragmentCatalogBinding
import com.ujujzk.ee.ui.di.Flow
import com.ujujzk.ee.ui.navigation.BackButtonListener
import com.ujujzk.ee.ui.navigation.FragmentScreen
import com.ujujzk.ee.ui.tools.addSystemBottomMargin
import com.ujujzk.ee.ui.tools.addSystemTopPadding
import kotlinx.android.synthetic.main.fragment_catalog.*


class CatalogFragment :
    BaseFragment<FragmentCatalogBinding, CatalogViewModel>(
        R.layout.fragment_catalog,
        CatalogViewModel::class
    ),
    BackButtonListener {

    override val displayNavBar: Boolean = false

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun onBackPressed(): Boolean =
        viewModel.onBackPressed()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        toolbar.addSystemTopPadding()
        addBtn.addSystemBottomMargin()

    }

    class Screen(flowName: Flow) : FragmentScreen({
        CatalogFragment().apply {
            arguments = Bundle().apply { putSerializable(EXTRA_FLOW_QUALIFIER, flowName)}
        }
    })


}