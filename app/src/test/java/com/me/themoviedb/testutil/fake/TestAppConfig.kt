package com.me.themoviedb.testutil.fake

import com.me.themoviedb.data.datasource.local.AppConfig

class TestAppConfig : AppConfig {
    private var baseUrl: String? = null
    private var imageSize: String? = null

    override suspend fun setImageBaseUrl(baseUrl: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getImageBaseUrl(): String {
        return TEST_URL
    }

    override suspend fun setImageSize(size: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getImageSize(): String {
        return TEST_IMAGE_SIZE
    }

    companion object {
        const val TEST_URL = "TEST_DEFAULT_URL/"
        const val TEST_IMAGE_SIZE = "TEST_IMAGE_SIZE"
    }
}