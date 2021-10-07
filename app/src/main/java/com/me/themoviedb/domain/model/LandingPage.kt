package com.me.themoviedb.domain.model

data class LandingPage(
    val currentPage: Int,
    val totalPage: Int,
    val movies: List<Movie>,
)