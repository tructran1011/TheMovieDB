package com.me.themoviedb.common.helper

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface AppConfig {

    suspend fun setImageBaseUrl(baseUrl: String)
    suspend fun getImageBaseUrl(): String

    suspend fun setImageSize(size: String)
    suspend fun getImageSize(): String
}

class DataStoreAppConfig @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : AppConfig {

    override suspend fun setImageBaseUrl(baseUrl: String) {
        dataStore.edit { pref ->
            pref[PrefKey.IMAGE_BASE_URL] = baseUrl
        }
    }

    override suspend fun getImageBaseUrl(): String {
        return dataStore
            .data
            .map { it[PrefKey.IMAGE_BASE_URL] }
            .first()
            ?: DEFAULT_BASE_URL
    }

    override suspend fun setImageSize(size: String) {
        dataStore.edit { pref ->
            pref[PrefKey.IMAGE_SIZE] = size
        }
    }

    override suspend fun getImageSize(): String {
        return dataStore
            .data
            .map { it[PrefKey.IMAGE_SIZE] }
            .first()
            ?: DEFAULT_SIZE
    }

    private object PrefKey {
        val IMAGE_BASE_URL = stringPreferencesKey("image-base-url")
        val IMAGE_SIZE = stringPreferencesKey("image-size")
    }

    companion object {
        private const val DEFAULT_BASE_URL = "http://image.tmdb.org/t/p/"
        private const val DEFAULT_SIZE = "original"
    }
}