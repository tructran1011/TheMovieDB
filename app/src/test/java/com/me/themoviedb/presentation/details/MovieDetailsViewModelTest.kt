package com.me.themoviedb.presentation.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.me.themoviedb.common.Result
import com.me.themoviedb.domain.usecase.movie.FetchMovieCreditsUseCase
import com.me.themoviedb.domain.usecase.movie.FetchMovieDetailsUseCase
import com.me.themoviedb.testutil.MainCoroutineRule
import com.me.themoviedb.testutil.capturesAllValues
import com.me.themoviedb.testutil.fake.FakeDataGenerator
import com.me.themoviedb.testutil.observeForTesting
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.Rule
import org.junit.Test

class MovieDetailsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val testCoroutineDispatcher = mainCoroutineRule.dispatcher

    private val fetchDetailsUseCase: FetchMovieDetailsUseCase = mockk()
    private val fetchCreditsUseCase: FetchMovieCreditsUseCase = mockk()

    @Test
    fun fetchData_bothDetailsAndCreditsSuccess_shouldShowData() = testCoroutineDispatcher.runBlockingTest {
        val mockDetails = FakeDataGenerator.createMovieDetails()
        val mockCredits = FakeDataGenerator.createMovieCredits()

        coEvery {
            fetchDetailsUseCase(any())
        } answers {
            flowOf(
                Result.loading(),
                Result.success(mockDetails)
            )
        }

        coEvery {
            fetchCreditsUseCase(any())
        } answers {
            flowOf(
                Result.loading(),
                Result.success(mockCredits)
            )
        }

        val viewModel = createViewModel()

        // Captures all values of isLoading
        viewModel.isLoading.capturesAllValues { values ->
            // Observe LiveData to read value
            listOf(viewModel.data, viewModel.error).observeForTesting {
                // Trigger fetch
                viewModel.fetch(1)

                // Loading values
                assertThat(values, equalTo(listOf(false, true, false)))

                // Error values: should be null
                assertThat(viewModel.error.value?.getContentIfNotHandled(), nullValue())

                // Data
                val detailsWithCredits = viewModel.data.value
                val details = detailsWithCredits?.details
                val credits = detailsWithCredits?.credits
                val showAll = detailsWithCredits?.showAll

                assertThat(details, equalTo(mockDetails))
                assertThat(credits, equalTo(mockCredits))
                assertThat(showAll, equalTo(false))
            }
        }
    }

    @Test
    fun fetchData_detailsApiFailed_shouldShowError() = testCoroutineDispatcher.runBlockingTest {
            val mockCredits = FakeDataGenerator.createMovieCredits()

            coEvery {
                fetchDetailsUseCase(any())
            } answers {
                flowOf(
                    Result.loading(),
                    Result.error(Throwable())
                )
            }

            coEvery {
                fetchCreditsUseCase(any())
            } answers {
                flowOf(
                    Result.loading(),
                    Result.success(mockCredits)
                )
            }

            val viewModel = createViewModel()

            // Captures all values of isLoading
            viewModel.isLoading.capturesAllValues { values ->
                // Observe LiveData to read value
                listOf(viewModel.data, viewModel.error).observeForTesting {
                    // Trigger fetch
                    viewModel.fetch(1)

                    // Loading values
                    assertThat(values, equalTo(listOf(false, true, false)))

                    // Error value
                    assertThat(viewModel.error.value?.getContentIfNotHandled(), notNullValue())

                    // Data should be null
                    assertThat(viewModel.data.value, nullValue())
                }
            }
        }

    @Test
    fun fetchData_creditsApiFailed_shouldShowError() = testCoroutineDispatcher.runBlockingTest {
        val mockDetails = FakeDataGenerator.createMovieDetails()

        coEvery {
            fetchDetailsUseCase(any())
        } answers {
            flowOf(
                Result.loading(),
                Result.success(mockDetails)
            )
        }

        coEvery {
            fetchCreditsUseCase(any())
        } answers {
            flowOf(
                Result.loading(),
                Result.error(Throwable())
            )
        }

        val viewModel = createViewModel()

        // Captures all values of isLoading
        viewModel.isLoading.capturesAllValues { values ->
            // Observe LiveData to read value
            listOf(viewModel.data, viewModel.error).observeForTesting {
                // Trigger fetch
                viewModel.fetch(1)

                // Loading values
                assertThat(values, equalTo(listOf(false, true, false)))

                // Error value
                assertThat(viewModel.error.value?.getContentIfNotHandled(), notNullValue())

                // Data should be null
                assertThat(viewModel.data.value, nullValue())
            }
        }
    }

    @Test
    fun fetchData_bothApisFailed_shouldShowError() = testCoroutineDispatcher.runBlockingTest {

        coEvery {
            fetchDetailsUseCase(any())
        } answers {
            flowOf(
                Result.loading(),
                Result.error(Throwable())
            )
        }

        coEvery {
            fetchCreditsUseCase(any())
        } answers {
            flowOf(
                Result.loading(),
                Result.error(Throwable())
            )
        }

        val viewModel = createViewModel()

        // Captures all values of isLoading
        viewModel.isLoading.capturesAllValues { values ->
            // Observe LiveData to read value
            listOf(viewModel.data, viewModel.error).observeForTesting {
                // Trigger fetch
                viewModel.fetch(1)

                // Loading values
                assertThat(values, equalTo(listOf(false, true, false)))

                // Error value
                assertThat(viewModel.error.value?.getContentIfNotHandled(), notNullValue())

                // Data should be null
                assertThat(viewModel.data.value, nullValue())
            }
        }
    }

    @Test
    fun showAllChanged() = testCoroutineDispatcher.runBlockingTest {
        val mockDetails = FakeDataGenerator.createMovieDetails()
        val mockCredits = FakeDataGenerator.createMovieCredits()

        coEvery {
            fetchDetailsUseCase(any())
        } answers {
            flowOf(
                Result.loading(),
                Result.success(mockDetails)
            )
        }

        coEvery {
            fetchCreditsUseCase(any())
        } answers {
            flowOf(
                Result.loading(),
                Result.success(mockCredits)
            )
        }

        val viewModel = createViewModel()

        // Captures all values of isLoading
        // Observe LiveData to read value
        viewModel.data.observeForTesting {
            // Trigger fetch
            viewModel.fetch(1)

            // Data
            val detailsWithCredits = viewModel.data.value
            val details = detailsWithCredits?.details
            val credits = detailsWithCredits?.credits
            val showAll = detailsWithCredits?.showAll

            assertThat(details, equalTo(mockDetails))
            assertThat(credits, equalTo(mockCredits))
            assertThat(showAll, equalTo(false))

            // Change `Show All`
            viewModel.onShowAllChanged(true)
            assertThat(viewModel.data.value?.showAll, equalTo(true))
        }
    }

    private fun createViewModel() =
        MovieDetailsViewModel(fetchDetailsUseCase, fetchCreditsUseCase)
}