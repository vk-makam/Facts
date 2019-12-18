package com.vsr2.mobile.facts.views.base

import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.vsr2.mobile.facts.viewmodels.base.BaseViewModel

abstract class BaseActivity<TViewBinding : ViewDataBinding, TViewModel : BaseViewModel> :
    AppCompatActivity() {

    // Contains the layoutId of Activity
    abstract val layoutId: Int

    // Returns the ViewModel class object
    abstract val viewModelClass: Class<TViewModel>

    // Returns the BindingResource of ViewModel
    abstract val viewModelBR: Int

    protected lateinit var viewModel: TViewModel
    protected lateinit var viewBinding: TViewBinding

    /**
     * Initializes the UI components here.
     * Implement this method in your Activity class
     */
    protected open fun initUi(bundle: Bundle?) {
        // TODO: UI initialization goes here
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        // Check for the valid LayoutId and setContentView
        val layoutId = layoutId
        require(!(0 == layoutId || -1 == layoutId)) { "Set the LayoutId in Activity class" }

        // Set contentView and bind the ViewModel property
        viewBinding = DataBindingUtil.setContentView(this, layoutId)
        viewModel = ViewModelProviders.of(this).get(viewModelClass)
        viewBinding.setVariable(viewModelBR, viewModel)

        // Observe title and set
        viewModel.title.observe(this, Observer<String> { title -> supportActionBar?.title = title })

        // Initialize the UI components here
        initUi(savedInstanceState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (android.R.id.home == item.itemId) {
            supportFragmentManager.popBackStack()
        }

        return super.onOptionsItemSelected(item)
    }
}
