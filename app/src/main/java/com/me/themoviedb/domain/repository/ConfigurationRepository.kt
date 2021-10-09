package com.me.themoviedb.domain.repository

import com.me.themoviedb.common.Result

interface ConfigurationRepository {

    suspend fun fetchConfiguration(): Result<Unit>
}