package com.me.themoviedb.presentation.details.adapter.viewholder

import android.view.View
import com.me.themoviedb.R
import com.me.themoviedb.databinding.ItemInfoBinding
import com.me.themoviedb.presentation.details.adapter.item.InfoItem

class InfoViewHolder(view: View) : MovieDetailsViewHolder(view) {

    private val binding = ItemInfoBinding.bind(view)

    override fun displayInfo(infoItem: InfoItem) {
        binding.tvInfo.text = infoItem.info
    }

    companion object {
        const val LAYOUT_ID = R.layout.item_info
    }
}