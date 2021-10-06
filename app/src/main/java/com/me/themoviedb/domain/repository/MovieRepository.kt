package com.me.themoviedb.domain.repository

import com.me.themoviedb.common.Result
import com.me.themoviedb.domain.model.LandingPage

interface MovieRepository {

    suspend fun getNowPlaying(page: Int): Result<LandingPage>

}