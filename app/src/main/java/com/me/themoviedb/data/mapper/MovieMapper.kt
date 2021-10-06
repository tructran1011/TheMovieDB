package com.me.themoviedb.data.mapper

import com.me.themoviedb.data.datasource.remote.dto.LandingPageDto
import com.me.themoviedb.domain.model.LandingPage
import com.me.themoviedb.domain.model.Movie

fun LandingPageDto.toLandingPage(): LandingPage {
    val movies =
        results?.mapNotNull { it.toMovie() } ?: emptyList()

    return LandingPage(
        currentPage = page ?: 0,
        totalPage = totalPages ?: 0,
        movies = movies
    )
}

fun LandingPageDto.ResultDto.toMovie(): Movie? =
    if (id == null) {
        null
    } else {
        Movie(
            id = id,
            title = this.title ?: "",
            overview = this.overview ?: "",
            releaseDate = this.releaseDate ?: "",
            voteAverage = voteAverage ?: 0F,
            image = backdropPath ?: "",
        )
    }