package com.me.themoviedb.di.module

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.me.themoviedb.common.helper.DefaultTimeProvider
import com.me.themoviedb.common.helper.TimeProvider
import com.me.themoviedb.data.datasource.local.AppConfig
import com.me.themoviedb.data.datasource.local.DataStoreAppConfig
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindAppConfig(impl: DataStoreAppConfig): AppConfig

    @Binds
    @Singleton
    abstract fun bindTimeProvider(impl: DefaultTimeProvider): TimeProvider

    companion object {
        @Provides
        @Singleton
        fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
            PreferenceDataStoreFactory.create(
                produceFile = {
                    context.preferencesDataStoreFile("app-config")
                }
            )
    }
}