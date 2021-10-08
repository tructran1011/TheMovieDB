package com.me.themoviedb.presentation.details.adapter.item

import com.me.themoviedb.presentation.details.adapter.viewholder.MovieDetailsViewHolder
import com.me.themoviedb.presentation.details.adapter.viewholder.OverviewViewHolder

data class OverviewItem(
    val overview: String,
) : MovieDetailsItem() {
    override val type = OverviewViewHolder.LAYOUT_ID

    override fun sameAs(item: MovieDetailsItem): Boolean {
        return item is OverviewItem
    }

    override fun displayIn(holder: MovieDetailsViewHolder) {
        holder.displayOverview(this)
    }
}