package com.vsr2.mobile.facts.views.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.vsr2.mobile.facts.R
import com.vsr2.mobile.facts.listeners.OnBackPressedListener
import com.vsr2.mobile.facts.viewmodels.base.BaseViewModel

abstract class BaseFragment<TViewBinding : ViewDataBinding, TViewModel : BaseViewModel> :
    Fragment(), OnBackPressedListener {

    /**
     * Returns the current Fragments's LayoutId
     */
    protected abstract val layoutId: Int

    /**
     * Returns the current Fragments's title
     */
    protected open val title: Int = R.string.app_name

    /**
     * Returns the current Fragments's custom title, which is not from resources (strings.xml)
     */
    protected open val customTitle: String = ""

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

    /**
     * Default implementation of back pressed event
     * To customize, override in Fragment
     * */
    override fun onBackPressed() {

        // Default behaviour of back pressed event
        parentActivity.supportFragmentManager.popBackStack()
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

        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()

        // Get titleId
        val titleId = title

        // Check for valid titleId
        if (-1 == titleId || 0 == titleId) {
            parentActivity.title = customTitle
        } else {
            parentActivity.setTitle(titleId)
        }
    }
}