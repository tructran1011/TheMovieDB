package com.me.themoviedb.presentation.details.adapter.item

import com.me.themoviedb.domain.model.MovieCredits
import com.me.themoviedb.presentation.details.adapter.viewholder.MemberViewHolder
import com.me.themoviedb.presentation.details.adapter.viewholder.MovieDetailsViewHolder

class MemberItem(val member: MovieCredits.Member) : MovieDetailsItem() {
    override val type = MemberViewHolder.LAYOUT_ID

    override fun sameAs(item: MovieDetailsItem): Boolean {
        return item is MemberItem && item.member.id == member.id
    }

    override fun displayIn(holder: MovieDetailsViewHolder) {
        holder.displayMember(this)
    }
}