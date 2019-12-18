package com.vsr2.mobile.facts.views.home

import android.os.Bundle
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

        viewModel.facts.observe(
            viewLifecycleOwner,
            Observer<FactsResponse> { response ->
                response?.apply {
                    viewModel.title.postValue(response.title)
                    adapter.facts = response.rows
                }
            })
    }

    override fun initUi(bundle: Bundle?) {
        super.initUi(bundle)

        // Prepare the RecyclerView
        facts_list.setHasFixedSize(false)
        facts_list.layoutManager = LinearLayoutManager(parentActivity)
        facts_list.adapter = adapter
    }
}
