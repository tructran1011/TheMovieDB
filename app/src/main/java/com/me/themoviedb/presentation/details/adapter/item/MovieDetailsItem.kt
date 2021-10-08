package com.me.themoviedb.presentation.details.adapter.item

import androidx.recyclerview.widget.DiffUtil
import com.me.themoviedb.presentation.details.adapter.viewholder.MovieDetailsViewHolder

sealed class MovieDetailsItem {

    fun bind(holder: MovieDetailsViewHolder) {
        displayIn(holder)
    }

    abstract val type: Int

    abstract fun sameAs(item: MovieDetailsItem): Boolean

    abstract fun displayIn(holder: MovieDetailsViewHolder)

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieDetailsItem>() {
            override fun areItemsTheSame(
                oldItem: MovieDetailsItem,
                newItem: MovieDetailsItem,
            ): Boolean {
                return oldItem.sameAs(newItem)
            }

            override fun areContentsTheSame(
                oldItem: MovieDetailsItem,
                newItem: MovieDetailsItem,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}