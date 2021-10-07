package com.me.themoviedb.presentation.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.me.themoviedb.common.util.load
import com.me.themoviedb.databinding.ItemMemberBinding
import com.me.themoviedb.domain.model.MovieCredits

class MemberAdapter :
    ListAdapter<MovieCredits.Member, MemberAdapter.MemberViewHolder>(MovieCredits.Member.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val binding = ItemMemberBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MemberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MemberViewHolder(
        private val binding: ItemMemberBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(member: MovieCredits.Member) {
            binding.run {
                ivAvatar.load(member.avatar)
                tvName.text = member.name
                tvJob.text = member.job
            }
        }
    }
}