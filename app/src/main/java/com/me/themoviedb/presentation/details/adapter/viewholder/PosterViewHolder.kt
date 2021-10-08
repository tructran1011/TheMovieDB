package com.me.themoviedb.presentation.details.adapter.viewholder

import android.view.View
import com.me.themoviedb.R
import com.me.themoviedb.common.util.load
import com.me.themoviedb.databinding.ItemPosterBinding
import com.me.themoviedb.presentation.details.adapter.item.PosterItem

class PosterViewHolder(view: View) : MovieDetailsViewHolder(view) {

    private val binding = ItemPosterBinding.bind(view)

    override fun displayPoster(posterItem: PosterItem) {
        binding.ivPoster.load(posterItem.imageUrl)
    }

    companion object {
        const val LAYOUT_ID = R.layout.item_poster
    }
}