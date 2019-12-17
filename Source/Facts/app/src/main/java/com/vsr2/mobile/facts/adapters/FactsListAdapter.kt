package com.vsr2.mobile.facts.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.vsr2.mobile.facts.R
import com.vsr2.mobile.facts.databinding.ItemFactBinding
import com.vsr2.mobile.facts.models.Fact

class FactsListAdapter : RecyclerView.Adapter<FactViewHolder>() {

    // Holds the items to render
    var facts: List<Fact> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = facts.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val viewBinding =
            DataBindingUtil.inflate<ItemFactBinding>(inflater, R.layout.item_fact, parent, false)
        return FactViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: FactViewHolder, position: Int) {
        // TODO: Validate the Fact, before assigning
        holder.viewBinding.fact = facts[position]
    }
}

class FactViewHolder(val viewBinding: ItemFactBinding) : RecyclerView.ViewHolder(viewBinding.root)
