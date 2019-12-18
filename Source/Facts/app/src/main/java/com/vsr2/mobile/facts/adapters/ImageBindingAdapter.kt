package com.vsr2.mobile.facts.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.vsr2.mobile.facts.R

object ImageBindingAdapter {

    @JvmStatic
    @BindingAdapter("app:imageUrl")
    fun setImageUrl(view: ImageView, url: String?) {

        Glide.with(view.context)
            .load(url)
            .placeholder(R.drawable.american_beaver)
            .error(R.drawable.american_beaver)
            .dontAnimate()
            .fitCenter()
            .into(view)
    }
}