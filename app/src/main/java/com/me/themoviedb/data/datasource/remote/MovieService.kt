package com.me.themoviedb.data.datasource.remote

import com.me.themoviedb.data.datasource.remote.dto.LandingPageDto
import com.me.themoviedb.data.datasource.remote.dto.MovieCreditsDto
import com.me.themoviedb.data.datasource.remote.dto.MovieDetailsDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("page") page: Int
    ) : LandingPageDto

    @GET("movie/top_ratedddd")
    suspend fun getTopRated(
        @Query("page") page: Int
    ) : LandingPageDto

    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") id: Int
    ) : MovieDetailsDto

    @GET("movie/{id}/credits")
    suspend fun getMovieCredits(
        @Path("id") id: Int
    ) : MovieCreditsDto
}