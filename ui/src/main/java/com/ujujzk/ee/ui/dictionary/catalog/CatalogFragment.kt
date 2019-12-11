package com.ujujzk.ee.ui.dictionary.catalog

import android.os.Bundle
import android.util.Log
import com.ujujzk.ee.ui.R
import com.ujujzk.ee.ui.base.BaseFragment
import com.ujujzk.ee.ui.databinding.FragmentCatalogBinding
import com.ujujzk.ee.ui.di.Flow
import com.ujujzk.ee.ui.navigation.BackButtonListener
import com.ujujzk.ee.ui.navigation.FragmentScreen


class CatalogFragment :
    BaseFragment<FragmentCatalogBinding, CatalogViewModel>(
        R.layout.fragment_catalog,
        CatalogViewModel::class
    ),
    BackButtonListener {


    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun onBackPressed(): Boolean =
        viewModel.onBackPressed()

    class Screen(flowName: Flow) : FragmentScreen({
        CatalogFragment().apply {
            arguments = Bundle().apply { putSerializable(EXTRA_FLOW_QUALIFIER, flowName)}
        }
    })


}