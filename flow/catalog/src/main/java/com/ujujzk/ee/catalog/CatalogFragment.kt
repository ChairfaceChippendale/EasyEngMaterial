package com.ujujzk.ee.catalog

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.core.view.MarginLayoutParamsCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.ujujzk.ee.catalog.databinding.FragmentCatalogBinding
import com.ujujzk.ee.presentation.base.BaseFragment
import com.ujujzk.ee.presentation.base.InitViews
import com.ujujzk.ee.presentation.base.VMObserver
import com.ujujzk.ee.presentation.base.viewBinding
import com.ujujzk.ee.presentation.navigation.BackButtonListener
import com.ujujzk.ee.presentation.navigation.switchnav.NavBarOwner
import com.ujujzk.ee.presentation.tools.*
import dev.chrisbanes.insetter.applySystemWindowInsetsToMargin
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding


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

        rtToolbar.applySystemWindowInsetsToMargin(top = true)
        addBtn.applySystemWindowInsetsToMargin(bottom = true)
        dictionaries.applySystemWindowInsetsToPadding(top = true, bottom = true)


//        ViewCompat.setOnApplyWindowInsetsListener(rtToolbar) { v, insets ->
//            val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
//            val navigationBarInsets = insets.getInsets(WindowInsetsCompat.Type.navigationBars())
//            Log.e("FFFF", "${statusBarInsets.top}")
//            rtToolbar.postUpdateLayoutParams<ViewGroup.MarginLayoutParams> {
//                topMargin = statusBarInsets.top
//            }
//            insets
//        }

        dictionaries.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                //rise toolbar to scroll list "behind" it
                if (!recyclerView.canScrollVertically(-1)) {
                    // we have reached the top of the list
                    rtToolbar.elevation = 0f
                } else {
                    // we are not at the top yet
                    rtToolbar.elevation = resources.getDimensionPixelSize(R.dimen.toolbar_elevation).toFloat()
                }
                //hide add FAB on scroll list down
                if (dy > 0) {
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