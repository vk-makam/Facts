package com.vsr2.mobile.facts.views.home

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.vsr2.mobile.facts.BR
import com.vsr2.mobile.facts.R
import com.vsr2.mobile.facts.databinding.ActivityMainBinding
import com.vsr2.mobile.facts.viewmodels.home.MainViewModel
import com.vsr2.mobile.facts.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override val layoutId: Int
        get() = R.layout.activity_main

    override val viewModelClass: Class<MainViewModel>
        get() = MainViewModel::class.java

    override val viewModelBR: Int
        get() = BR.viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        // TODO: Yet to debug, Why BaseActivity.onCreate() is not getting called
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // Configure the AppBar
        val navController: NavController = findNavController(R.id.navigation_host_fragment)
        appBarConfiguration = AppBarConfiguration.Builder(R.id.home_view).build()
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.navigation_host_fragment).navigateUp() || super.onSupportNavigateUp()
    }
}
