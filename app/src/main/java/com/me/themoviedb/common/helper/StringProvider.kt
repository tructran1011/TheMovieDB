package com.me.themoviedb.common.helper

import com.me.themoviedb.data.datasource.local.AppConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StringProvider @Inject constructor(
    private val appConfig: AppConfig
){
    suspend fun getFullImageUrl(path: String?): String {
        if (path.isNullOrBlank()) {
            return ""
        }

        val baseUrl = appConfig.getImageBaseUrl()
        val size = appConfig.getImageSize()
        return baseUrl + size + path
    }
}