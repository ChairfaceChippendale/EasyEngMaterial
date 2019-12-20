package com.ujujzk.ee.ui.vocabulary.store

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override val displayNavBar: Boolean = true

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun onBackPressed(): Boolean =
        viewModel.onBackPressed()

    class Screen : FragmentScreen({ StoreFragment() })
}