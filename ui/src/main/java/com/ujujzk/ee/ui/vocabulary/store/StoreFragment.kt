package com.ujujzk.ee.ui.vocabulary.store

import com.ujujzk.ee.ui.R
import com.ujujzk.ee.ui.base.BaseFragment
import com.ujujzk.ee.ui.databinding.FragmentStoreBinding
import com.ujujzk.ee.ui.navigation.BackButtonListener
import com.ujujzk.ee.ui.navigation.FragmentScreen


class StoreFragment :
    BaseFragment<FragmentStoreBinding, StoreViewModel>(
        R.layout.fragment_store,
        StoreViewModel::class
    ),
    BackButtonListener {

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun onBackPressed(): Boolean =
        viewModel.onBackPressed()

    class Screen : FragmentScreen({ StoreFragment() })
}