package com.ujujzk.ee.ui.dictionary.catalog

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.ujujzk.ee.ui.R
import com.ujujzk.ee.ui.base.BaseFragment
import com.ujujzk.ee.ui.databinding.FragmentCatalogBinding
import com.ujujzk.ee.ui.di.Flow
import com.ujujzk.ee.ui.navigation.BackButtonListener
import com.ujujzk.ee.ui.navigation.FragmentScreen
import com.ujujzk.ee.ui.tools.*
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

        toolbar.addSystemTopMargin()
        addBtn.addSystemBottomMargin()
        dictionaries.addSystemTopBottomPadding()

        dictionaries.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                //rise toolbar to scroll list "behind" it
                if(!recyclerView.canScrollVertically(-1)) {
                    // we have reached the top of the list
                    toolbar.elevation = 0f
                } else {
                    // we are not at the top yet
                    toolbar.elevation = resources.getDimensionPixelSize(R.dimen.toolbar_elevation).toFloat()
                }
                //hide add FAB on scroll list down
                if (dy > 0){
                    addBtn.hide()
                } else {
                    addBtn.show()
                }
            }
        })
    }

    class Screen(flowName: Flow) : FragmentScreen({
        CatalogFragment().apply {
            arguments = Bundle().apply { putSerializable(EXTRA_FLOW_QUALIFIER, flowName)}
        }
    })
}