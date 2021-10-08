package com.me.themoviedb.presentation.details.adapter.viewholder

import android.view.View
import com.me.themoviedb.R
import com.me.themoviedb.databinding.ItemCastNCrewsLabelBinding
import com.me.themoviedb.presentation.details.adapter.item.CastAndCrewsLabelItem

class CastAndCrewsLabelViewHolder(view: View) : MovieDetailsViewHolder(view) {

    private val binding = ItemCastNCrewsLabelBinding.bind(view)

    override fun displayCastAndCrewsLabel(castAndCrewsLabelItem: CastAndCrewsLabelItem) {
        binding.run {
            tvCastNCrews.text = castAndCrewsLabelItem.label
            val resId = if (castAndCrewsLabelItem.showAll) {
                R.string.see_less
            } else {
                R.string.see_all
            }
            tvSeeAll.setText(resId)
        }
    }

    companion object {
        const val LAYOUT_ID = R.layout.item_cast_n_crews_label
    }
}