package com.me.themoviedb.domain.usecase

import com.me.themoviedb.common.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

/**
 * Executes business logic in its execute method and keep posting updates to the result as
 * [Result].
 * Handling an exception (emit [Result.error] to the result) is the sub classes's responsibility.
 */
abstract class FlowUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {

    suspend operator fun invoke(parameter: P): Flow<Result<R>> =
        execute(parameter)
            .catch { cause: Throwable -> emit(Result.error(cause, null)) }
            .flowOn(coroutineDispatcher)

    protected abstract fun execute(parameter: P): Flow<Result<R>>
}