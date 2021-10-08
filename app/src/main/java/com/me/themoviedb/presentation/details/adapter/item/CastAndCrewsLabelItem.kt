package com.me.themoviedb.presentation.details.adapter.item

import com.me.themoviedb.presentation.details.adapter.viewholder.CastAndCrewsLabelViewHolder
import com.me.themoviedb.presentation.details.adapter.viewholder.MovieDetailsViewHolder

data class CastAndCrewsLabelItem(
    val label: String,
    val showAll: Boolean,
) : MovieDetailsItem() {
    override val type = CastAndCrewsLabelViewHolder.LAYOUT_ID

    override fun sameAs(item: MovieDetailsItem): Boolean {
        return item is CastAndCrewsLabelItem
    }

    override fun displayIn(holder: MovieDetailsViewHolder) {
        holder.displayCastAndCrewsLabel(this)
    }
}