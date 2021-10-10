package com.me.themoviedb.data.repository

import com.me.themoviedb.common.helper.StringProvider
import com.me.themoviedb.common.isError
import com.me.themoviedb.common.isSuccess
import com.me.themoviedb.data.datasource.remote.MovieService
import com.me.themoviedb.data.datasource.remote.dto.MovieCreditsDto
import com.me.themoviedb.di.module.NetworkModule
import com.me.themoviedb.domain.model.MovieCredits
import com.me.themoviedb.domain.model.MovieCredits.Member.MemberType.Cast
import com.me.themoviedb.domain.model.MovieCredits.Member.MemberType.Crew
import com.me.themoviedb.domain.repository.MovieRepository
import com.me.themoviedb.testutil.LISTING_PAGE_1
import com.me.themoviedb.testutil.MOVIE_CREDITS
import com.me.themoviedb.testutil.MOVIE_DETAILS
import com.me.themoviedb.testutil.enqueueFile
import com.me.themoviedb.testutil.fake.TestAppConfig
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class MovieRepositoryImplTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var  movieService: MovieService
    private lateinit var stringProvider: StringProvider

    private lateinit var movieRepository: MovieRepository

    @Before
    fun setUp() {
        stringProvider = StringProvider(TestAppConfig())

        mockWebServer = MockWebServer()

        val okHttpClient = OkHttpClient.Builder()
            .build()
        val gson = NetworkModule.provideGson()
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(mockWebServer.url("/"))
            .build()
        movieService = NetworkModule.provideMovieService(retrofit)

        movieRepository = MovieRepositoryImpl(movieService, stringProvider)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getNowPlaying_success() = runBlocking {
        mockWebServer.enqueueFile(LISTING_PAGE_1)
        val result = movieRepository.getNowPlaying(1)

        assertThat(result.isSuccess(), equalTo(true))

        val landingPage = result.data

        assertThat(landingPage?.currentPage, equalTo(1))

        assertThat(landingPage?.totalPage, equalTo(2))

        assertThat(landingPage?.movies, hasSize(2))

        assertThat(landingPage?.movies?.map { it.id }, equalTo(listOf(1, 2)))
    }

    @Test
    fun getNowPlaying_error() = runBlocking {
        mockWebServer.enqueue(MockResponse().setResponseCode(500))
        val result = movieRepository.getNowPlaying(1)

        assertThat(result.isError(), equalTo(true))

        assertThat(result.throwable, instanceOf(HttpException::class.java))
    }

    @Test
    fun getTopRated_success() = runBlocking {
        mockWebServer.enqueueFile(LISTING_PAGE_1)
        val result = movieRepository.getTopRated(1)

        assertThat(result.isSuccess(), equalTo(true))

        val landingPage = result.data

        assertThat(landingPage?.currentPage, equalTo(1))

        assertThat(landingPage?.totalPage, equalTo(2))

        assertThat(landingPage?.movies, hasSize(2))

        assertThat(landingPage?.movies?.map { it.id }, equalTo(listOf(1, 2)))
    }

    @Test
    fun getTopRated_error() = runBlocking {
        mockWebServer.enqueue(MockResponse().setResponseCode(500))
        val result = movieRepository.getTopRated(1)

        assertThat(result.isError(), equalTo(true))

        assertThat(result.throwable, instanceOf(HttpException::class.java))
    }

    @Test
    fun getMovieDetails_success() = runBlocking {
        mockWebServer.enqueueFile(MOVIE_DETAILS)
        val result = movieRepository.getMovieDetails(1)

        assertThat(result.isSuccess(), equalTo(true))

        val movieDetails = result.data

        assertThat(movieDetails?.title, equalTo("Venom: Let There Be Carnage"))
        assertThat(movieDetails?.year, equalTo("2021"))
        assertThat(movieDetails?.voteAvg, equalTo(7.4F))
        assertThat(movieDetails?.duration, equalTo("1h 37m"))
        assertThat(movieDetails?.genres, equalTo(listOf("Science Fiction", "Action")))
        assertThat(movieDetails?.overview, equalTo("After finding a host body in investigative reporter Eddie Brock, the alien symbiote must face a new enemy, Carnage, the alter ego of serial killer Cletus Kasady."))
    }

    @Test
    fun getMovieDetails_error() = runBlocking {
        mockWebServer.enqueue(MockResponse().setResponseCode(500))
        val result = movieRepository.getMovieDetails(1)

        assertThat(result.isError(), equalTo(true))

        assertThat(result.throwable, instanceOf(HttpException::class.java))
    }

    @Test
    fun getMovieCredits_success() = runBlocking {
        mockWebServer.enqueueFile(MOVIE_CREDITS)
        val result = movieRepository.getMovieCredits(1)

        assertThat(result.isSuccess(), equalTo(true))

        val movieCredits = result.data

        assertThat(movieCredits?.members?.map { it.id }, equalTo(listOf(2524, 57755, 149, 1333)))
        assertThat(movieCredits?.members?.map { it.type }, equalTo(listOf(Cast, Cast, Crew, Crew)))
        assertThat(movieCredits?.directors, equalTo(listOf("Andy Serkis")))
    }

    @Test
    fun getMovieCredits_error() = runBlocking {
        mockWebServer.enqueue(MockResponse().setResponseCode(500))
        val result = movieRepository.getMovieDetails(1)

        assertThat(result.isError(), equalTo(true))

        assertThat(result.throwable, instanceOf(HttpException::class.java))
    }
}