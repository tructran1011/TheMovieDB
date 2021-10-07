package com.me.themoviedb.domain.model

data class MovieCredits(
    val members: List<Member>,
    val directors: List<String>,
) {

    val displayDirectorNames = when(directors.size) {
        0 -> ""
        1 -> directors[0]
        2 -> "${directors[0]} & ${directors[0]}"
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
            object Director : MemberType()
        }
    }
}