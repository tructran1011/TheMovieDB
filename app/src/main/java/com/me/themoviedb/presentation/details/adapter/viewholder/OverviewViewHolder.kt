package com.me.themoviedb.presentation.details.adapter.viewholder

import android.view.View
import com.me.themoviedb.R
import com.me.themoviedb.databinding.ItemOverviewBinding
import com.me.themoviedb.presentation.details.adapter.item.OverviewItem
import timber.log.Timber

class OverviewViewHolder(view: View) : MovieDetailsViewHolder(view) {

    private val binding = ItemOverviewBinding.bind(view)

    override fun displayOverview(overviewItem: OverviewItem) {
        Timber.d("Overview: $overviewItem")
        binding.tvOverview.text = overviewItem.overview
    }

    companion object {
        const val LAYOUT_ID = R.layout.item_overview
    }
}