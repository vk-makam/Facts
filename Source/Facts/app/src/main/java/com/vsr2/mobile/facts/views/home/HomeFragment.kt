package com.vsr2.mobile.facts.views.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsr2.mobile.facts.BR
import com.vsr2.mobile.facts.R
import com.vsr2.mobile.facts.adapters.FactsListAdapter
import com.vsr2.mobile.facts.databinding.FragmentHomeBinding
import com.vsr2.mobile.facts.models.FactsResponse
import com.vsr2.mobile.facts.viewmodels.home.HomeViewModel
import com.vsr2.mobile.facts.views.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    private var adapter: FactsListAdapter = FactsListAdapter()

    override val layoutId: Int
        get() = R.layout.fragment_home

    override val viewModelBR: Int
        get() = BR.viewModel

    override val viewModelClass: Class<HomeViewModel>
        get() = HomeViewModel::class.java

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Observe the Facts from ViewModel and update the FactsListAdapter
        viewModel.facts.observe(
            viewLifecycleOwner,
            Observer<FactsResponse> { response ->
                response?.apply {
                    viewModel.title.postValue(response.title)
                    adapter.facts = response.rows
                }
            })

        // Show or hide the no_data_layout
        viewModel.noDataLayoutVisibility.observe(viewLifecycleOwner, Observer<Int> { visibility ->
            no_data_layout.visibility = visibility
        })

        // Show or hide the  Shimmer animation
        viewModel.isLoading.observe(viewLifecycleOwner, Observer<Boolean> { isLoading ->

            // TODO: Implement custom RecyclerView to handle Shimmer animation
            if (isLoading) {
                shimmer_layout.visibility = View.VISIBLE
                pull_to_refresh.visibility = View.GONE
            } else {
                shimmer_layout.visibility = View.GONE
                pull_to_refresh.visibility = View.VISIBLE
            }
        })
    }

    override fun initUi(bundle: Bundle?) {
        super.initUi(bundle)

        // Prepare the RecyclerView
        facts_list.setHasFixedSize(false)
        facts_list.layoutManager = LinearLayoutManager(parentActivity)
        facts_list.adapter = adapter

        // Handle pull to refresh
        pull_to_refresh.setOnRefreshListener {

            // Fetch the Facts from server
            viewModel.refreshFacts()

            // Hide the progress bar
            pull_to_refresh.isRefreshing = false
        }
    }
}
