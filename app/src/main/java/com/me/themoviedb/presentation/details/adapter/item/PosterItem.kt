package com.me.themoviedb.presentation.details.adapter.item

import com.me.themoviedb.presentation.details.adapter.viewholder.MovieDetailsViewHolder
import com.me.themoviedb.presentation.details.adapter.viewholder.PosterViewHolder

data class PosterItem(
    val imageUrl: String,
) : MovieDetailsItem() {
    override val type = PosterViewHolder.LAYOUT_ID

    override fun sameAs(item: MovieDetailsItem): Boolean {
        return item is PosterItem
    }

    override fun displayIn(holder: MovieDetailsViewHolder) {
        holder.displayPoster(this)
    }
}