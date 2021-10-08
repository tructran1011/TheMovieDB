package com.me.themoviedb.data.repository

import com.me.themoviedb.common.Result
import com.me.themoviedb.data.datasource.local.AppConfig
import com.me.themoviedb.data.datasource.remote.ConfigurationService
import com.me.themoviedb.domain.repository.ConfigurationRepository
import timber.log.Timber
import javax.inject.Inject

class ConfigurationRepositoryImpl @Inject constructor(
    private val configurationService: ConfigurationService,
    private val appConfig: AppConfig,
) : ConfigurationRepository {

    override suspend fun fetchConfiguration(): Result<Unit> {
        return try {
            val configDto = configurationService.getConfiguration()
            val baseUrl = configDto.images?.secureBaseUrl ?: ""
            val imageSize = configDto.images?.backdropSizes?.lastOrNull() ?: ""

            Timber.d("Config: $baseUrl - $imageSize")
            if (baseUrl.isNotEmpty()) {
                appConfig.setImageBaseUrl(baseUrl)
            }

            if (imageSize.isNotEmpty()) {
                appConfig.setImageSize(imageSize)
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Result.error(e)
        }
    }
}