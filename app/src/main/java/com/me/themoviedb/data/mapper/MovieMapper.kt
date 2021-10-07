package com.me.themoviedb.data.mapper

import com.me.themoviedb.common.helper.StringProvider
import com.me.themoviedb.data.datasource.remote.dto.LandingPageDto
import com.me.themoviedb.domain.model.LandingPage
import com.me.themoviedb.domain.model.Movie

suspend fun LandingPageDto.toLandingPage(
    createFullUrl: suspend (String) -> String
): LandingPage {
    val movies =
        results
            ?.mapNotNull { it.toMovie(createFullUrl) }
            ?: emptyList()

    return LandingPage(
        currentPage = page ?: 0,
        totalPage = totalPages ?: 0,
        movies = movies
    )
}

suspend fun LandingPageDto.ResultDto.toMovie(
    createFullUrl: suspend (String) -> String
): Movie? =
    if (id == null) {
        null
    } else {
        val fullImageUrl = createFullUrl(backdropPath ?: "")
        Movie(
            id = id,
            title = this.title ?: "",
            overview = this.overview ?: "",
            releaseDate = this.releaseDate ?: "",
            voteAverage = voteAverage ?: 0F,
            voteCount = voteCount ?: 0,
            image = fullImageUrl,
        )
    }