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
import io.reactivex.disposables.Disposable
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.reflect.KClass

abstract class BaseFragment<BINDING : ViewDataBinding, out VIEW_MODEL : BaseViewModel>(
    @LayoutRes private val layout: Int,
    viewModelClass: KClass<VIEW_MODEL>
) : Fragment() {

    private val registry: LifecycleRegistry
        get() = LifecycleRegistry(this)

    private val disposables: LifecycleAwareDisposables
        get() = LifecycleAwareDisposables(registry)

    protected lateinit var binding: BINDING
    protected val viewModel : VIEW_MODEL by viewModel(viewModelClass)

    protected abstract fun bindViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (layout > 0) {
            binding = DataBindingUtil.inflate(inflater, layout, container, false)
            binding.lifecycleOwner = this
            binding.root
        } else {
            throw IllegalArgumentException("Layout for ${binding.javaClass} is not specified!")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
    }

    protected fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

}