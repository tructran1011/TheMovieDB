package com.me.themoviedb.presentation.details.adapter.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.me.themoviedb.presentation.details.adapter.item.*

open class MovieDetailsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    class Factory(private val parent: ViewGroup) {
        private val inflater: LayoutInflater = LayoutInflater.from(parent.context)

        fun create(viewType: Int): MovieDetailsViewHolder {
            val view = inflater.inflate(viewType, parent, false)
            return when (viewType) {
                PosterViewHolder.LAYOUT_ID -> PosterViewHolder(view)
                TitleViewHolder.LAYOUT_ID -> TitleViewHolder(view)
                InfoViewHolder.LAYOUT_ID -> InfoViewHolder(view)
                OverviewViewHolder.LAYOUT_ID -> OverviewViewHolder(view)
                CastAndCrewsLabelViewHolder.LAYOUT_ID -> CastAndCrewsLabelViewHolder(view)
                MemberViewHolder.LAYOUT_ID -> MemberViewHolder(view)
                else -> throw IllegalArgumentException("Invalid viewType: $viewType")
            }
        }
    }

    open fun displayPoster(posterItem: PosterItem) {}
    open fun displayTitle(titleItem: TitleItem) {}
    open fun displayInfo(infoItem: InfoItem) {}
    open fun displayOverview(overviewItem: OverviewItem) {}
    open fun displayCastAndCrewsLabel(castAndCrewsLabelItem: CastAndCrewsLabelItem) {}
    open fun displayMember(memberItem: MemberItem) {}
}