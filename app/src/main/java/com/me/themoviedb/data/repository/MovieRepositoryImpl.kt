package com.me.themoviedb.data.repository

import com.me.themoviedb.common.Result
import com.me.themoviedb.common.helper.StringProvider
import com.me.themoviedb.data.datasource.remote.MovieService
import com.me.themoviedb.data.mapper.toLandingPage
import com.me.themoviedb.data.mapper.toMovieCredits
import com.me.themoviedb.data.mapper.toMovieDetails
import com.me.themoviedb.domain.model.LandingPage
import com.me.themoviedb.domain.model.MovieCredits
import com.me.themoviedb.domain.model.MovieDetails
import com.me.themoviedb.domain.repository.MovieRepository
import timber.log.Timber
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieService: MovieService,
    private val stringProvider: StringProvider
) : MovieRepository {

    override suspend fun getNowPlaying(page: Int): Result<LandingPage> {
        Timber.d("getNowPlaying $page")
        return try {
            val landingPage = movieService
                .getNowPlaying(page)
                .toLandingPage(stringProvider)
            Result.success(landingPage)
        } catch (e: Exception) {
            Result.error(e)
        }
    }

    override suspend fun getTopRated(page: Int): Result<LandingPage> {
        Timber.d("getTopRated $page")
        return try {
            val landingPage = movieService
                .getTopRated(page)
                .toLandingPage(stringProvider)
            Result.success(landingPage)
        } catch (e: Exception) {
            Result.error(e)
        }
    }

    override suspend fun getMovieDetails(id: Int): Result<MovieDetails> {
        return try {
            val movieDetails = movieService
                .getMovieDetails(id)
                .toMovieDetails(stringProvider)
            Result.success(movieDetails)
        } catch (e: Exception) {
            Result.error(e)
        }
    }

    override suspend fun getMovieCredits(id: Int): Result<MovieCredits> {
        return try {
            val movieCredits = movieService
                .getMovieCredits(id)
                .toMovieCredits(stringProvider)
            Result.success(movieCredits)
        } catch (e: Exception) {
            Result.error(e)
        }
    }
}