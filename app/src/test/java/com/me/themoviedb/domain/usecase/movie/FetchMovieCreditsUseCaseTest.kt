package com.me.themoviedb.domain.usecase.movie

import com.me.themoviedb.common.Result
import com.me.themoviedb.common.isError
import com.me.themoviedb.common.isLoading
import com.me.themoviedb.common.isSuccess
import com.me.themoviedb.domain.model.MovieCredits
import com.me.themoviedb.domain.model.MovieCredits.Member.MemberType.Cast
import com.me.themoviedb.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.notNullValue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class FetchMovieCreditsUseCaseTest {

    private val testDispatcher = TestCoroutineDispatcher()
    private val movieRepo: MovieRepository = mockk()
    private val useCase = FetchMovieCreditsUseCase(testDispatcher, movieRepo)

    @Test
    fun fetch_success() = testDispatcher.runBlockingTest {
        coEvery {
            movieRepo.getMovieCredits(any())
        } answers {
            Result.success(createFakeCredits())
        }

        val results = useCase(1).take(2).toList()

        assertThat(results[0].isLoading(), equalTo(true))

        assertThat(results[1].isSuccess(), equalTo(true))

        val credits = results[1].data

        assertThat(credits?.members?.map { it.id }, equalTo((1..10).toList()))

        assertThat(credits?.directors, equalTo(listOf("director1", "director2")))
    }

    @Test
    fun fetch_error() = testDispatcher.runBlockingTest {
        coEvery {
            movieRepo.getMovieCredits(any())
        } answers {
            Result.error(Throwable())
        }

        val results = useCase(1).take(2).toList()

        assertThat(results[0].isLoading(), equalTo(true))

        assertThat(results[1].isError(), equalTo(true))

        assertThat(results[1].throwable, notNullValue())
    }

    private fun createFakeCredits() =
        MovieCredits(
            members = createFakeMembers(10),
            directors = listOf("director1", "director2")
        )

    private fun createFakeMembers(count: Int) = (1..count).map { id ->
        MovieCredits.Member(
            id = id,
            avatar = "",
            name = "",
            job = "",
            type = Cast
        )
    }
}