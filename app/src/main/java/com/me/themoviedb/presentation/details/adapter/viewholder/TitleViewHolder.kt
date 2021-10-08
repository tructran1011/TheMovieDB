package com.me.themoviedb.presentation.details.adapter.viewholder

import android.view.View
import com.me.themoviedb.R
import com.me.themoviedb.databinding.ItemTitleBinding
import com.me.themoviedb.presentation.details.adapter.item.TitleItem

class TitleViewHolder(view: View) : MovieDetailsViewHolder(view) {

    private val binding = ItemTitleBinding.bind(view)

    override fun displayTitle(titleItem: TitleItem) {
        binding.tvTitle.text = titleItem.title
    }

    companion object {
        const val LAYOUT_ID = R.layout.item_title
    }
}