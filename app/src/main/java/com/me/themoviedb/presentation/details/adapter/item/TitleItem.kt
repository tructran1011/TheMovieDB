package com.me.themoviedb.presentation.details.adapter.item

import com.me.themoviedb.presentation.details.adapter.viewholder.MovieDetailsViewHolder
import com.me.themoviedb.presentation.details.adapter.viewholder.TitleViewHolder

data class TitleItem(
    val title: String,
    val needTopDivider: Boolean = false,
    val needBotDivider: Boolean = true,
) : MovieDetailsItem() {
    override val type = TitleViewHolder.LAYOUT_ID

    override fun sameAs(item: MovieDetailsItem): Boolean {
        return item is TitleItem
    }

    override fun displayIn(holder: MovieDetailsViewHolder) {
        holder.displayTitle(this)
    }
}