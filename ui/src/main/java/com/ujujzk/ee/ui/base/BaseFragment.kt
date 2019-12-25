package com.ujujzk.ee.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleRegistry
import com.ujujzk.ee.ui.navigation.NavBarOwner
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import kotlin.reflect.KClass

abstract class BaseFragment<BINDING : ViewDataBinding, out VIEW_MODEL : BaseViewModel>(
    @LayoutRes private val layout: Int,
    viewModelClass: KClass<VIEW_MODEL>
): Fragment() {

    companion object {
        const val EXTRA_FLOW_QUALIFIER = "EXTRA_FLOW_QUALIFIER"
    }

    private val disposables: LifecycleAwareDisposables by lazy { LifecycleAwareDisposables(LifecycleRegistry(this)) }

    protected lateinit var binding: BINDING
    protected val viewModel: VIEW_MODEL by viewModel(viewModelClass){
        //is used in case of reuse Fragment in different flows
        parametersOf(arguments?.getSerializable(EXTRA_FLOW_QUALIFIER))
    }

    protected abstract fun bindViewModel()

    protected abstract val displayNavBar: Boolean

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (layout > 0) {
            binding = DataBindingUtil.inflate(inflater, layout, container, false)
            binding.lifecycleOwner = this
            bindViewModel()
            binding.root
        } else {
            throw IllegalArgumentException("Layout for ${binding.javaClass} is not specified!")
        }
    }

    override fun onResume() {
        super.onResume()
        if (displayNavBar){
            (activity as NavBarOwner).showNavBar()
        } else {
            (activity as NavBarOwner).hideNaveBar()
        }
    }

}