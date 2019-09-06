package com.ujujzk.ee.ui.vocabulary.learn

import com.ujujzk.ee.ui.R
import com.ujujzk.ee.ui.base.BaseFragment
import com.ujujzk.ee.ui.databinding.FragmentLearnBinding
import com.ujujzk.ee.ui.navigation.BackButtonListener
import com.ujujzk.ee.ui.navigation.FragmentScreen


class LearnFragment:
    BaseFragment<FragmentLearnBinding, LearnViewModel>(R.layout.fragment_learn, LearnViewModel::class),
    BackButtonListener {

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun onBackPressed(): Boolean =
        viewModel.onBackPressed()

    class Screen : FragmentScreen({ LearnFragment() })
}