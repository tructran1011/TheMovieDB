package com.me.themoviedb.di.module

import com.me.themoviedb.data.repository.ConfigurationRepositoryImpl
import com.me.themoviedb.data.repository.MovieRepositoryImpl
import com.me.themoviedb.domain.repository.ConfigurationRepository
import com.me.themoviedb.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepository(impl: MovieRepositoryImpl): MovieRepository

    @Binds
    @Singleton
    abstract fun bindConfigurationRepository(impl: ConfigurationRepositoryImpl): ConfigurationRepository
}