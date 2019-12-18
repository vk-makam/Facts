package com.vsr2.mobile.facts.views.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.vsr2.mobile.facts.viewmodels.base.BaseViewModel

abstract class BaseFragment<TViewBinding : ViewDataBinding, TViewModel : BaseViewModel> :
    Fragment() {

    /**
     * Returns the current Fragments's LayoutId
     */
    protected abstract val layoutId: Int

    /**
     * Returns the ViewModel class
     */
    protected abstract val viewModelClass: Class<TViewModel>

    /**
     * Returns the BindingResource of ViewModel variable
     */
    protected abstract val viewModelBR: Int

    /**
     * Late initialization variables
     */
    protected lateinit var viewModel: TViewModel
    protected lateinit var viewBinding: TViewBinding
    protected lateinit var fragmentView: View
    protected lateinit var parentActivity: BaseActivity<*, *>

    /**
     * Initializes the UI components here.
     * Implement this method in your Fragment class
     */
    open fun initUi(bundle: Bundle?) {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        // Check for the valid LayoutId
        require(!(0 == layoutId || -1 == layoutId)) { "Set the LayoutId in Fragment class" }

        // Get the Parent Activity
        parentActivity = activity as BaseActivity<*, *>

        // Get the ViewModel
        viewModel = ViewModelProviders.of(parentActivity).get(viewModelClass)

        // Inflate the layout and bind the ViewModel BR
        viewBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        viewBinding.setVariable(viewModelBR, viewModel)
        fragmentView = viewBinding.root

        // Set the lifecycleOwner so DataBinding can observe LiveData
        viewBinding.lifecycleOwner = viewLifecycleOwner

        // Observe title and set
        viewModel.title.observe(
            viewLifecycleOwner,
            Observer<String> { title -> parentActivity.supportActionBar?.title = title })

        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi(savedInstanceState)
    }
}