package com.me.themoviedb.domain.model

import com.me.themoviedb.domain.model.MovieCredits.Member.MemberType.Crew

data class MovieCredits(
    val members: List<Member>,
) {
    val directors = members
        .filter { it.type == Crew && it.job == Member.JOB_DIRECTOR && it.name.isNotEmpty()}
        .map { it.name }

    val displayDirectorNames = when(directors.size) {
        0 -> ""
        1 -> directors[0]
        2 -> "${directors[0]} & ${directors[1]}"
        else -> "${directors[0]} and ${directors.size - 1} more"
    }

    data class Member(
        val id: Int,
        val avatar: String,
        val name: String,
        val job: String,
        val type: MemberType
    ) {
        sealed class MemberType {
            object Cast : MemberType()
            object Crew : MemberType()
        }

        companion object {
            const val JOB_DIRECTOR = "Director"
        }
    }
}