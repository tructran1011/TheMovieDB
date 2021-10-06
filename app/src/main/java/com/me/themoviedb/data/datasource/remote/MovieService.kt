package com.me.themoviedb.data.datasource.remote

import com.me.themoviedb.data.datasource.remote.dto.LandingPageDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("page") page: Int
    ) : LandingPageDto
}