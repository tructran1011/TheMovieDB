package com.me.themoviedb.data.datasource.remote

import com.me.themoviedb.data.datasource.remote.dto.ConfigurationDto
import retrofit2.http.GET

interface ConfigurationService {

    @GET("configuration")
    suspend fun getConfiguration(): ConfigurationDto
}