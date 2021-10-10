package com.me.themoviedb.presentation.landing.tab

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.me.themoviedb.common.Result
import com.me.themoviedb.domain.model.LandingPage
import com.me.themoviedb.domain.usecase.FlowUseCase
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
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MovieListingViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val testCoroutineDispatcher = mainCoroutineRule.dispatcher

    private val fetchListingUseCase: FlowUseCase<Int, LandingPage> = mockk()

    @Test
    fun fetchFirstPage_success() = testCoroutineDispatcher.runBlockingTest {
        coEvery {
            fetchListingUseCase(any())
        } answers {
            flowOf(
                Result.loading(),
                Result.success(FakeDataGenerator.createFirstPage(10))
            )
        }

        val viewModel = createViewModel()

        // Need to observe LiveData to make callbacks triggered
        viewModel.isLoading.capturesAllValues { values ->
            listOf(viewModel.landingItems, viewModel.fetchError).observeForTesting {

                viewModel.refresh()

                // Loading states
                assertThat(values, hasSize(3))

                assertThat(values[0], equalTo(false))

                assertThat(values[1], equalTo(true))

                assertThat(values[2], equalTo(false))

                // Landing items
                // m-m-m-A-m-m-m-A-m-m-m-A-m
                // 0-1-2-3-4-5-6-7-8-9-0-1-2
                val items = viewModel.landingItems.value
                assertThat(items, hasSize(13))

                assertThat(items?.subList(0, 3), everyItem(instanceOf(MovieItem::class.java)))
                assertThat(items?.get(3), instanceOf(AdsItem::class.java))

                assertThat(items?.subList(4, 7), everyItem(instanceOf(MovieItem::class.java)))
                assertThat(items?.get(7), instanceOf(AdsItem::class.java))

                assertThat(items?.subList(8, 11), everyItem(instanceOf(MovieItem::class.java)))
                assertThat(items?.get(11), instanceOf(AdsItem::class.java))

                assertThat(items?.get(12), instanceOf(MovieItem::class.java))

                // Error
                assertThat(viewModel.fetchError.value?.getContentIfNotHandled(), nullValue())
            }
        }
    }

    @Test
    fun fetchFirstPage_error() = testCoroutineDispatcher.runBlockingTest {
        coEvery {
            fetchListingUseCase(any())
        } answers {
            flowOf(
                Result.loading(),
                Result.error(Throwable())
            )
        }

        val viewModel = createViewModel()

        // Need to observe LiveData to make callbacks triggered
        viewModel.isLoading.capturesAllValues { values ->
            listOf(viewModel.landingItems, viewModel.fetchError).observeForTesting {

                viewModel.refresh()

                // Loading states
                assertThat(values, hasSize(3))

                assertThat(values[0], equalTo(false))

                assertThat(values[1], equalTo(true))

                assertThat(values[2], equalTo(false))

                // Landing items
                val items = viewModel.landingItems.value
                assertThat(items, nullValue())

                // Error
                assertThat(viewModel.fetchError.value?.getContentIfNotHandled(), notNullValue())
            }
        }
    }

    @Test
    fun fetch_canLoadMore() = testCoroutineDispatcher.runBlockingTest {
        coEvery {
            fetchListingUseCase(any())
        } answers {
            flowOf(
                Result.loading(),
                Result.success(FakeDataGenerator.createFirstPage(10))
            )
        }

        val viewModel = createViewModel()

        // Need to observe LiveData to make fetch flow triggered
        viewModel.isLoading.observeForTesting {
            viewModel.refresh()

            assertThat(viewModel.currentPage, equalTo(1))
            assertThat(viewModel.canLoadMore, equalTo(true))
        }
    }

    @Test
    fun fetch_canNotLoadMore() = testCoroutineDispatcher.runBlockingTest {
        coEvery {
            fetchListingUseCase(any())
        } answers {
            flowOf(
                Result.loading(),
                Result.success(
                    FakeDataGenerator.createPage(
                        page = 1,
                        pageCount = 1,
                        firstMovieId = 11,
                        lastMovieId = 20
                    )
                )
            )
        }

        val viewModel = createViewModel()

        // Need to observe LiveData to make fetch flow triggered
        viewModel.isLoading.observeForTesting {
            viewModel.refresh()

            assertThat(viewModel.currentPage, equalTo(1))
            assertThat(viewModel.canLoadMore, equalTo(false))
        }
    }

    @Test
    fun loadMore_success() = testCoroutineDispatcher.runBlockingTest {
        coEvery {
            fetchListingUseCase(any())
        } answers {
            flowOf(
                Result.loading(),
                Result.success(
                    FakeDataGenerator.createPage(
                        page = 1,
                        pageCount = 2,
                        firstMovieId = 1,
                        lastMovieId = 10
                    )
                )
            )
        }

        val viewModel = createViewModel()

        // Need to observe LiveData to make fetch flow triggered
        listOf(viewModel.isLoading, viewModel.landingItems).observeForTesting {
            // First page
            viewModel.refresh()

            // 10 movies, 3 ads
            assertThat(viewModel.landingItems.value, hasSize(13))

            assertThat(viewModel.currentPage, equalTo(1))
            assertThat(viewModel.canLoadMore, equalTo(true))

            // Load more
            coEvery { fetchListingUseCase(any()) } answers {
                flowOf(
                    Result.loading(),
                    Result.success(
                        FakeDataGenerator.createPage(
                            page = 2,
                            pageCount = 2,
                            firstMovieId = 11,
                            lastMovieId = 20
                        )
                    )
                )
            }
            viewModel.loadMore()


            // 20 movies, 6 ads
            assertThat(viewModel.landingItems.value, hasSize(26))

            assertThat(viewModel.currentPage, equalTo(2))
            assertThat(viewModel.canLoadMore, equalTo(false))
        }
    }

    @Test
    fun loadMore_error() = testCoroutineDispatcher.runBlockingTest {
        coEvery {
            fetchListingUseCase(any())
        } answers {
            flowOf(
                Result.loading(),
                Result.success(
                    FakeDataGenerator.createPage(
                        page = 1,
                        pageCount = 2,
                        firstMovieId = 1,
                        lastMovieId = 10
                    )
                )
            )
        }

        val viewModel = createViewModel()

        // Need to observe LiveData to make fetch flow triggered
        listOf(
            viewModel.isLoading,
            viewModel.landingItems,
            viewModel.fetchError
        ).observeForTesting {
            // First page
            viewModel.refresh()

            // 10 movies, 3 ads
            assertThat(viewModel.landingItems.value, hasSize(13))
            // No errors
            assertThat(viewModel.fetchError.value?.getContentIfNotHandled(), nullValue())

            assertThat(viewModel.currentPage, equalTo(1))
            assertThat(viewModel.canLoadMore, equalTo(true))

            // Load more
            coEvery { fetchListingUseCase(any()) } answers {
                flowOf(
                    Result.loading(),
                    Result.error(Throwable())
                )
            }
            viewModel.loadMore()


            // 10 movies, 3 ads
            assertThat(viewModel.landingItems.value, hasSize(13))
            // Has error
            assertThat(viewModel.fetchError.value?.getContentIfNotHandled(), notNullValue())

            assertThat(viewModel.currentPage, equalTo(1))
            assertThat(viewModel.canLoadMore, equalTo(true))
        }
    }

    private fun createViewModel() = MovieListingViewModel(fetchListingUseCase)
}