package com.ujujzk.ee.catalog

import androidx.recyclerview.widget.RecyclerView
import com.ujujzk.ee.catalog.databinding.FragmentCatalogBinding
import com.ujujzk.ee.presentation.base.BaseFragment
import com.ujujzk.ee.presentation.base.InitViews
import com.ujujzk.ee.presentation.base.VMObserver
import com.ujujzk.ee.presentation.base.viewBinding
import com.ujujzk.ee.presentation.navigation.BackButtonListener
import com.ujujzk.ee.presentation.navigation.switchnav.NavBarOwner
import com.ujujzk.ee.presentation.tools.*


class CatalogFragment :
    BaseFragment<FragmentCatalogBinding, CatalogViewModel>(
        R.layout.fragment_catalog,
        CatalogViewModel::class
    ),
    BackButtonListener {

    companion object {
        const val EXTRA_FLOW_QUALIFIER = "EXTRA_FLOW_QUALIFIER"
    }

    override val binding by viewBinding(FragmentCatalogBinding::bind)

    private val dicAdapter = DicAdapter()

    override val observer: VMObserver<CatalogViewModel> = {
        dictionaries.observe {
            dicAdapter.submitList(it)
        }
    }

    override val initViews: InitViews<FragmentCatalogBinding> = {

        btnBack.onClick { viewModel.onBackPressed() }

        rtToolbar.outlineProvider = TransparentOutlineProvider()
        rtToolbar.clipToOutline = true
        rtToolbar.addSystemTopMargin()

        addBtn.addSystemBottomMargin()
        dictionaries.addSystemTopBottomPadding()

        dictionaries.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                //rise toolbar to scroll list "behind" it
                if(!recyclerView.canScrollVertically(-1)) {
                    // we have reached the top of the list
                    rtToolbar.elevation = 0f
                } else {
                    // we are not at the top yet
                    rtToolbar.elevation = resources.getDimensionPixelSize(R.dimen.toolbar_elevation).toFloat()
                }
                //hide add FAB on scroll list down
                if (dy > 0){
                    binding.addBtn.hide()
                } else {
                    binding.addBtn.show()
                }
            }
        })
        dictionaries.adapter = dicAdapter
    }

    override fun onResume() {
        super.onResume()
        //CatalogFragment-Dictionary(Vocabulary)Parent-MainParent
        (parentFragment?.parentFragment as? NavBarOwner)?.hideNavBar()
    }

}