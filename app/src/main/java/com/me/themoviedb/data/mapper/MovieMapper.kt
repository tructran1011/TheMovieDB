package com.me.themoviedb.data.mapper

import com.me.themoviedb.common.helper.StringProvider
import com.me.themoviedb.common.util.getYear
import com.me.themoviedb.common.util.minutesToDuration
import com.me.themoviedb.data.datasource.remote.dto.LandingPageDto
import com.me.themoviedb.data.datasource.remote.dto.MovieCreditsDto
import com.me.themoviedb.data.datasource.remote.dto.MovieDetailsDto
import com.me.themoviedb.domain.model.LandingPage
import com.me.themoviedb.domain.model.Movie
import com.me.themoviedb.domain.model.MovieCredits
import com.me.themoviedb.domain.model.MovieDetails

suspend fun LandingPageDto.toLandingPage(
    stringProvider: StringProvider
): LandingPage {
    val movies =
        results
            ?.mapNotNull { it.toMovie(stringProvider) }
            ?: emptyList()

    return LandingPage(
        currentPage = page ?: 0,
        totalPage = totalPages ?: 0,
        movies = movies
    )
}

suspend fun LandingPageDto.ResultDto.toMovie(
    stringProvider: StringProvider
): Movie? =
    if (id == null) {
        null
    } else {
        val fullImageUrl = stringProvider.getFullImageUrl(backdropPath)
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

suspend fun MovieDetailsDto.toMovieDetails(
    stringProvider: StringProvider
) : MovieDetails {
    val fullImageUrl = stringProvider.getFullImageUrl(posterPath)
    return MovieDetails(
        image = fullImageUrl,
        title = title ?: "",
        year = releaseDate?.getYear() ?: "",
        voteAvg = voteAverage ?: 0.0f,
        duration = runtime?.minutesToDuration() ?: "",
        genres = genres?.mapNotNull { it.name } ?: emptyList(),
        overview = overview ?: "",
    )
}

suspend fun MovieCreditsDto.toMovieCredits(
    stringProvider: StringProvider
): MovieCredits {
    val cast = cast?.mapNotNull { it.toMember(stringProvider) } ?: emptyList()
    val crews = crew?.mapNotNull { it.toMember(stringProvider) } ?: emptyList()
    val directors = crews
        .filter { it.job == "Director" && it.name.isNotEmpty()}
        .map { it.name }

    return MovieCredits(
        members = cast.plus(crews),
        directors = directors
    )
}

suspend fun MovieCreditsDto.CastDto.toMember(
    stringProvider: StringProvider
): MovieCredits.Member? =
    if (id == null) {
        null
    } else {
        MovieCredits.Member(
            id = id,
            avatar = stringProvider.getFullImageUrl(profilePath),
            name = name ?: "",
            type = MovieCredits.Member.MemberType.Cast,
            job = if (gender == 1) {
                "Actress"
            } else {
                "Actor"
            }
        )
    }

suspend fun MovieCreditsDto.CrewDto.toMember(
    stringProvider: StringProvider,
): MovieCredits.Member? =
    if (id == null) {
        null
    } else {
        MovieCredits.Member(
            id = id,
            avatar = stringProvider.getFullImageUrl(profilePath),
            name = name ?: "",
            type = MovieCredits.Member.MemberType.Crew,
            job = job ?: "",
        )
    }