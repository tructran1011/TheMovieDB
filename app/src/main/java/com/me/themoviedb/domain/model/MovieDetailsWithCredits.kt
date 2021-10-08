package com.me.themoviedb.domain.model

data class MovieDetailsWithCredits(
    val details: MovieDetails,
    val credits: MovieCredits,
    val showAll: Boolean,
)
