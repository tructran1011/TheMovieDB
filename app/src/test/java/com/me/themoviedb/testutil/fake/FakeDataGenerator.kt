package com.me.themoviedb.testutil.fake

import com.me.themoviedb.domain.model.LandingPage
import com.me.themoviedb.domain.model.Movie

object FakeDataGenerator {


    fun createFirstPage(movieCount: Int) =
        createPage(
            page = 1,
            pageCount = 2,
            firstMovieId = 1,
            lastMovieId = movieCount,
        )

    fun createPage(page: Int, pageCount: Int, firstMovieId: Int, lastMovieId: Int) =
        LandingPage(
            currentPage = page,
            totalPage = pageCount,
            movies = createMovies(firstMovieId, lastMovieId)
        )

    fun createMovies(firstId: Int, lastId: Int) = (firstId..lastId).map { id ->
        Movie(
            id = id,
            title = "",
            overview = "",
            releaseDate = "",
            voteAverage = 0.0f,
            voteCount = 0,
            image = ""
        )
    }
}