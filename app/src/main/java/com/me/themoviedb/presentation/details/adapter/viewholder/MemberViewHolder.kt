package com.me.themoviedb.presentation.details.adapter.viewholder

import android.view.View
import com.me.themoviedb.R
import com.me.themoviedb.common.util.load
import com.me.themoviedb.databinding.ItemMemberBinding
import com.me.themoviedb.presentation.details.adapter.item.MemberItem

class MemberViewHolder(view: View) : MovieDetailsViewHolder(view) {

    private val binding = ItemMemberBinding.bind(view)

    override fun displayMember(memberItem: MemberItem) {
        val member = memberItem.member
        binding.run {
            ivAvatar.load(member.avatar, R.drawable.ic_person)
            tvName.text = member.name
            tvJob.text = member.job
        }
    }

    companion object {
        const val LAYOUT_ID = R.layout.item_member
    }
}