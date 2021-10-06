package com.me.themoviedb.presentation.landing

import androidx.recyclerview.widget.DiffUtil
import com.me.themoviedb.domain.model.Ads
import com.me.themoviedb.domain.model.Movie

sealed class LandingItem {
    abstract val type: Int

    fun bind(holder: LandingViewHolder) {
        displayIn(holder)
    }

    abstract fun displayIn(holder: LandingViewHolder)

    abstract fun sameAs(item: LandingItem): Boolean

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<LandingItem>() {
            override fun areItemsTheSame(oldItem: LandingItem, newItem: LandingItem): Boolean {
                return oldItem.sameAs(newItem)
            }

            override fun areContentsTheSame(oldItem: LandingItem, newItem: LandingItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}

data class MovieItem(val movie: Movie) : LandingItem() {
    override val type = MovieViewHolder.LAYOUT_ID

    override fun displayIn(holder: LandingViewHolder) {
        holder.displayMovie(this)
    }

    override fun sameAs(item: LandingItem): Boolean {
        return item is MovieItem && item.movie.id == this.movie.id
    }
}

data class AdsItem(val ads: Ads) : LandingItem() {
    override val type = AdsViewHolder.LAYOUT_ID

    override fun displayIn(holder: LandingViewHolder) {
        holder.displayAds(this)
    }

    override fun sameAs(item: LandingItem): Boolean {
        return item is AdsItem
    }
}