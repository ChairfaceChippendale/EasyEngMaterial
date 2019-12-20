package com.ujujzk.ee.ui.vocabulary.pack

import com.ujujzk.ee.ui.R
import com.ujujzk.ee.ui.base.BaseFragment
import com.ujujzk.ee.ui.databinding.FragmentPackBinding
import com.ujujzk.ee.ui.navigation.BackButtonListener
import com.ujujzk.ee.ui.navigation.FragmentScreen


class PackFragment :
    BaseFragment<FragmentPackBinding, PackViewModel>(
        R.layout.fragment_pack,
        PackViewModel::class
    ),
    BackButtonListener {

    override val displayNavBar: Boolean = true

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun onBackPressed(): Boolean =
        viewModel.onBackPressed()

    class Screen : FragmentScreen({ PackFragment() })
}