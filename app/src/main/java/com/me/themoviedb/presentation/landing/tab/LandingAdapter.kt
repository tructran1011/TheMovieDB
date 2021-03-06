package com.me.themoviedb.presentation.landing.tab

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.me.themoviedb.domain.model.Movie

class LandingAdapter(
    private val onItemClick: (Movie) -> Unit,
) : ListAdapter<LandingItem, LandingViewHolder>(LandingItem.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LandingViewHolder {
        return LandingViewHolder.Factory(
            inflater = LayoutInflater.from(parent.context),
            viewGroup = parent,
            onMovieClick = onItemClick
        ).create(viewType)
    }

    override fun onBindViewHolder(holder: LandingViewHolder, position: Int) {
        getItem(position)?.bind(holder)
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).type
    }
}