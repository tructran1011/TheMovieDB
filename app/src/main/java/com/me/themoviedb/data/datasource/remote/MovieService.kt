package com.me.themoviedb.data.datasource.remote

import com.me.themoviedb.data.datasource.remote.dto.LandingPageDto
import com.me.themoviedb.data.datasource.remote.dto.MovieCreditsDto
import com.me.themoviedb.data.datasource.remote.dto.MovieDetailsDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET(NOW_PLAYING)
    suspend fun getNowPlaying(
        @Query("page") page: Int
    ) : LandingPageDto

    @GET(TOP_RATED)
    suspend fun getTopRated(
        @Query("page") page: Int
    ) : LandingPageDto

    @GET(MOVIE_DETAILS)
    suspend fun getMovieDetails(
        @Path(ID) id: Int
    ) : MovieDetailsDto

    @GET(MOVIE_CREDITS)
    suspend fun getMovieCredits(
        @Path(ID) id: Int
    ) : MovieCreditsDto

    companion object {
        const val NOW_PLAYING = "movie/now_playing"
        const val TOP_RATED = "movie/top_rated"
        const val ID = "id"
        const val MOVIE_DETAILS = "movie/{$ID}"
        const val MOVIE_CREDITS = "movie/{$ID}/credits"
    }
}