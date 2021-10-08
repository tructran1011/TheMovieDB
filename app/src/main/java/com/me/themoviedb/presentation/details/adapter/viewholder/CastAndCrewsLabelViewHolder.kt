package com.me.themoviedb.presentation.details.adapter.viewholder

import android.view.View
import com.me.themoviedb.R
import com.me.themoviedb.databinding.ItemCastNCrewsLabelBinding
import com.me.themoviedb.presentation.details.adapter.MovieDetailsAdapter
import com.me.themoviedb.presentation.details.adapter.item.CastAndCrewsLabelItem
import timber.log.Timber

class CastAndCrewsLabelViewHolder(
    view: View,
    itemInteractListener: MovieDetailsAdapter.ItemInteractListener,
) : MovieDetailsViewHolder(view) {

    private val binding = ItemCastNCrewsLabelBinding.bind(view)
    private var showAll: Boolean? = null

    init {
        binding.tvSeeAll.setOnClickListener {
            showAll?.let { itemInteractListener.onShowAllClick(it) }
        }
    }

    override fun displayCastAndCrewsLabel(castAndCrewsLabelItem: CastAndCrewsLabelItem) {
        this.showAll = castAndCrewsLabelItem.showAll
        binding.run {
            tvCastNCrews.text = castAndCrewsLabelItem.label
            val resId = if (castAndCrewsLabelItem.showAll) {
                R.string.see_less
            } else {
                R.string.see_all
            }
            tvSeeAll.setText(resId)
            Timber.d("Show all: $showAll")
        }
    }

    companion object {
        const val LAYOUT_ID = R.layout.item_cast_n_crews_label
    }
}