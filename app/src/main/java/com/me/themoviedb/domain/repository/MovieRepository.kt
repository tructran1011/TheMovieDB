package com.me.themoviedb.domain.repository

import com.me.themoviedb.common.Result
import com.me.themoviedb.domain.model.LandingPage
import com.me.themoviedb.domain.model.MovieCredits
import com.me.themoviedb.domain.model.MovieDetails

interface MovieRepository {

    suspend fun getNowPlaying(page: Int): Result<LandingPage>

    suspend fun getTopRated(page: Int): Result<LandingPage>

    suspend fun getMovieDetails(id: Int): Result<MovieDetails>

    suspend fun getMovieCredits(id: Int): Result<MovieCredits>

}