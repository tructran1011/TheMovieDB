package com.me.themoviedb.common.helper

import com.me.themoviedb.testutil.fake.TestAppConfig
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.Test

class StringProviderTest {

    @Test
    fun generateFullUrl() = runBlockingTest {
        val stringProvider = StringProvider(TestAppConfig())

        assertThat(stringProvider.getFullImageUrl(null), equalTo(""))

        assertThat(stringProvider.getFullImageUrl(""), equalTo(""))

        assertThat(stringProvider.getFullImageUrl("  "), equalTo(""))

        assertThat(stringProvider.getFullImageUrl("/test"), equalTo("${TestAppConfig.TEST_URL}${TestAppConfig.TEST_IMAGE_SIZE}/test"))
    }
}