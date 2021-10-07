package com.me.themoviedb.domain.usecase.movie

import com.me.themoviedb.common.Result
import com.me.themoviedb.di.qualifier.IoDispatcher
import com.me.themoviedb.domain.model.MovieDetails
import com.me.themoviedb.domain.repository.MovieRepository
import com.me.themoviedb.domain.usecase.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FetchMovieDetailsUseCase @Inject constructor(
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher,
    private val movieRepository: MovieRepository,
) : FlowUseCase<Int, MovieDetails>(coroutineDispatcher) {

    override fun execute(parameter: Int): Flow<Result<MovieDetails>> {
        return flow {
            emit(Result.loading())
            emit(movieRepository.getMovieDetails(parameter))
        }
    }
}