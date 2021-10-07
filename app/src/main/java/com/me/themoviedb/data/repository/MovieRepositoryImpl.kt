package com.me.themoviedb.data.repository

import com.me.themoviedb.common.Result
import com.me.themoviedb.common.helper.StringProvider
import com.me.themoviedb.data.datasource.remote.MovieService
import com.me.themoviedb.data.mapper.toLandingPage
import com.me.themoviedb.domain.model.LandingPage
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
                .toLandingPage(this::createFullUrl)
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
                .toLandingPage(this::createFullUrl)
            Result.success(landingPage)
        } catch (e: Exception) {
            Result.error(e)
        }
    }

    private suspend fun createFullUrl(path: String): String =
        stringProvider.getFullImageUrl(path)
}