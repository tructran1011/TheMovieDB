package com.me.themoviedb.domain.model

import com.me.themoviedb.common.util.getYear

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val voteAverage: Float,
    val voteCount: Int,
    val image: String,
) {

    val year: String
        get() = releaseDate.getYear()
}