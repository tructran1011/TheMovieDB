package com.me.themoviedb.domain.model

data class MovieDetails(
    val image: String,
    val title: String,
    val year: String,
    val voteAvg: Float,
    val duration: String,
    val genres: List<String>,
    val overview: String,
)
