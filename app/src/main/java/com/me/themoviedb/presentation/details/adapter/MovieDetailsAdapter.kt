package com.me.themoviedb.presentation.details.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.me.themoviedb.presentation.details.adapter.item.MovieDetailsItem
import com.me.themoviedb.presentation.details.adapter.viewholder.MovieDetailsViewHolder

class MovieDetailsAdapter (private val listener: ItemInteractListener) :
    ListAdapter<MovieDetailsItem, MovieDetailsViewHolder>(MovieDetailsItem.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieDetailsViewHolder {
        return MovieDetailsViewHolder
            .Factory(parent, listener)
            .create(viewType)
    }

    override fun onBindViewHolder(holder: MovieDetailsViewHolder, position: Int) {
        getItem(position).bind(holder)
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).type
    }

    interface ItemInteractListener {
        fun onShowAllClick(showAll: Boolean)
    }
}