package com.me.themoviedb.domain.usecase.configuration

import com.me.themoviedb.common.Result
import com.me.themoviedb.di.qualifier.IoDispatcher
import com.me.themoviedb.domain.repository.ConfigurationRepository
import com.me.themoviedb.domain.usecase.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FetchConfigurationUseCase @Inject constructor(
    private val configurationRepository: ConfigurationRepository,
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher,
) : FlowUseCase<Unit, Unit>(coroutineDispatcher) {

    override fun execute(parameter: Unit): Flow<Result<Unit>> {
        return flow {
            emit(Result.loading())
            emit(configurationRepository.fetchConfiguration())
        }
    }
}