package com.ujujzk.ee.ui.dictionary.catalog

import com.ujujzk.ee.ui.R
import com.ujujzk.ee.ui.base.BaseFragment
import com.ujujzk.ee.ui.databinding.FragmentCatalogBinding
import com.ujujzk.ee.ui.navigation.BackButtonListener
import com.ujujzk.ee.ui.navigation.FragmentScreen
import org.koin.core.qualifier.Qualifier


class CatalogFragment(qualifier: Qualifier) :
    BaseFragment<FragmentCatalogBinding, CatalogViewModel>(R.layout.fragment_catalog, CatalogViewModel::class, qualifier),
    BackButtonListener
{

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun onBackPressed(): Boolean =
        viewModel.onBackPressed()

    class Screen(qualifier: Qualifier) : FragmentScreen({ CatalogFragment(qualifier) })
}