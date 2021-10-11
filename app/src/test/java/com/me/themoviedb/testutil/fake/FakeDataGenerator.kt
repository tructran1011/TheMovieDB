package com.me.themoviedb.testutil.fake

import com.me.themoviedb.domain.model.LandingPage
import com.me.themoviedb.domain.model.Movie
import com.me.themoviedb.domain.model.MovieCredits
import com.me.themoviedb.domain.model.MovieCredits.Member.MemberType
import com.me.themoviedb.domain.model.MovieCredits.Member.MemberType.Cast
import com.me.themoviedb.domain.model.MovieCredits.Member.MemberType.Crew
import com.me.themoviedb.domain.model.MovieDetails

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

    private fun createMovies(firstId: Int, lastId: Int) = (firstId..lastId).map { id ->
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

    fun createMovieDetails() = MovieDetails(
        image = "test image",
        title = "test title",
        year = "2021",
        voteAvg = 10.0f,
        duration = "1h 30m",
        genres = listOf("action", "horror"),
        overview = "test overview"
    )

    fun createMovieCredits(): MovieCredits {
        val casts = createMembersList(1, 5, FakeMemberType.CAST)
        val crews = createMembersList(5, 10, FakeMemberType.CREW)
        val directors = createMembersList(11, 15, FakeMemberType.DIRECTOR)
        return MovieCredits(members = casts.plus(crews).plus(directors))
    }

    fun createMembersList(firstId: Int, lastId: Int, type: FakeMemberType) =
        (firstId..lastId).map { id ->
            createMember(id, type)
        }

    fun createMember(
        id: Int,
        type: FakeMemberType
    ) = MovieCredits.Member(
        id = id,
        avatar = "avatar $id",
        name = "name $id",
        job = type.job,
        type = type.type
    )

    enum class FakeMemberType(val job: String, val type: MemberType) {
        CAST("actor", Cast),
        CREW("crew", Crew),
        DIRECTOR(MovieCredits.Member.JOB_DIRECTOR, Crew),
    }
}