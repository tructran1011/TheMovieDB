package com.me.themoviedb.presentation.details.adapter.item

import com.me.themoviedb.presentation.details.adapter.viewholder.InfoViewHolder
import com.me.themoviedb.presentation.details.adapter.viewholder.MovieDetailsViewHolder

data class InfoItem(
    val info: String,
) : MovieDetailsItem() {
    override val type = InfoViewHolder.LAYOUT_ID

    override fun sameAs(item: MovieDetailsItem): Boolean {
        return item is InfoItem
    }

    override fun displayIn(holder: MovieDetailsViewHolder) {
        holder.displayInfo(this)
    }
}