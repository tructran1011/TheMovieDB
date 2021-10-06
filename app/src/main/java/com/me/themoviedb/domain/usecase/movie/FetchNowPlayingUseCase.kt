package com.me.themoviedb.domain.usecase.movie

import com.me.themoviedb.common.Result
import com.me.themoviedb.di.qualifier.IoDispatcher
import com.me.themoviedb.domain.model.LandingPage
import com.me.themoviedb.domain.repository.MovieRepository
import com.me.themoviedb.domain.usecase.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FetchNowPlayingUseCase @Inject constructor(
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher,
    private val movieRepository: MovieRepository,
) : FlowUseCase<Int, LandingPage>(coroutineDispatcher) {

    override fun execute(parameter: Int): Flow<Result<LandingPage>> {
        return flow {
            emit(Result.loading())
            emit(movieRepository.getNowPlaying(parameter))
        }
    }
}