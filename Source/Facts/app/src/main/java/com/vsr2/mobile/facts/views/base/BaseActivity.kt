package com.vsr2.mobile.facts.views.base

import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.vsr2.mobile.facts.R
import com.vsr2.mobile.facts.listeners.OnBackPressedListener
import com.vsr2.mobile.facts.viewmodels.base.BaseViewModel

abstract class BaseActivity<TViewBinding : ViewDataBinding, TViewModel : BaseViewModel> :
    AppCompatActivity(), FragmentManager.OnBackStackChangedListener {

    // Contains the layoutId of Activity
    abstract val layoutId: Int

    // Returns the ViewModel class object
    abstract val viewModelClass: Class<TViewModel>

    // Returns the BindingResource of ViewModel
    abstract val viewModelBR: Int

    protected lateinit var viewModel: TViewModel
    protected lateinit var viewBinding: TViewBinding

    protected lateinit var topFragment: BaseFragment<*, *>

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

        // Updated the global variables
        supportFragmentManager.addOnBackStackChangedListener(this)

        // Initialize the UI components here
        initUi(savedInstanceState)
    }

    override fun onBackStackChanged() {

        // Get the current Fragment and update the mFragment
        topFragment =
            supportFragmentManager.findFragmentById(R.id.contentPanel) as BaseFragment<*, *>
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (android.R.id.home == item.itemId) {
            supportFragmentManager.popBackStack()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {

        // Pass the event to current Fragment, if its able to handle
        if (topFragment is OnBackPressedListener) {
            (topFragment as OnBackPressedListener).onBackPressed()
        } else {
            super.onBackPressed()
        }
    }

    /**
     * Launches the given Fragment
     */
    fun launchFragment(fragment: BaseFragment<*, *>, addToBackStack: Boolean) {
        topFragment = fragment

        val tag = fragment.javaClass.simpleName
        try {
            supportFragmentManager.popBackStack(
                tag,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.contentPanel, topFragment, tag)
        if (addToBackStack) {
            transaction.addToBackStack(tag)
        }

        transaction.show(topFragment)
        transaction.commitAllowingStateLoss()
    }
}
