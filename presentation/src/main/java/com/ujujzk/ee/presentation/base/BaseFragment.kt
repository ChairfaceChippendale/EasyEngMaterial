package com.ujujzk.ee.presentation.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.ujujzk.ee.presentation.R
import com.ujujzk.ee.presentation.navigation.BackButtonListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import kotlin.reflect.KClass

abstract class BaseFragment<BINDING : ViewBinding, VIEW_MODEL : BaseViewModel>(
    @LayoutRes private val layout: Int,
    viewModelClass: KClass<VIEW_MODEL>
): Fragment(layout), BackButtonListener {

    protected abstract val binding: BINDING
    protected val viewModel: VIEW_MODEL by viewModel(viewModelClass){
        //use Fragment arguments as a parameters for ViewModel constructor
        parametersOf(arguments)
    }

    abstract val observer: VMObserver<VIEW_MODEL>

    abstract val initViews: InitViews<BINDING>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewModel) {
            commonError.observe { showErrorToast(R.string.common_error) }
            networkError.observe { showErrorToast(R.string.network_error) }
        }
        initViews.invoke(binding)
        observer.invoke(viewModel)
    }

    protected fun showErrorToast(@StringRes messageId: Int){
        context?.let {ctx ->
            Toast.makeText(ctx, messageId, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed(): Boolean = viewModel.onBackPressed()

    inline fun <reified T> LiveData<T>.observe(crossinline callback: (T) -> Unit) {
        observe(this@BaseFragment.viewLifecycleOwner, Observer { callback.invoke(it) })
    }

    inline fun <reified T> LiveData<T?>.observeNonNull(crossinline callback: (T) -> Unit) {
        this.observe { it?.let(callback) }
    }
}

typealias VMObserver<VM> = VM.() -> Unit
typealias InitViews<B> = B.() -> Unit

