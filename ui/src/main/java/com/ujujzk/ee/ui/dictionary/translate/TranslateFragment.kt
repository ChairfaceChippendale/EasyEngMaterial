package com.ujujzk.ee.ui.dictionary.translate

import com.ujujzk.ee.ui.R
import com.ujujzk.ee.ui.base.BaseFragment
import com.ujujzk.ee.ui.databinding.FragmentTranslateBinding
import com.ujujzk.ee.ui.navigation.BackButtonListener
import com.ujujzk.ee.ui.navigation.FragmentScreen


class TranslateFragment :
    BaseFragment<FragmentTranslateBinding, TranslateViewModel>(R.layout.fragment_translate, TranslateViewModel::class),
    BackButtonListener
{

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun onBackPressed(): Boolean =
        viewModel.onBackPressed()

    class Screen : FragmentScreen({ TranslateFragment() })
}