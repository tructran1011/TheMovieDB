package com.me.themoviedb.presentation.landing.tab

import androidx.lifecycle.*
import com.me.themoviedb.common.*
import com.me.themoviedb.domain.model.Ads
import com.me.themoviedb.domain.model.LandingPage
import com.me.themoviedb.domain.model.Movie
import com.me.themoviedb.domain.usecase.FlowUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

open class MovieListingViewModel constructor(
    private val fetchListingUseCase: FlowUseCase<Int, LandingPage>,
) : ViewModel() {

    private var currentPage = 0

    private var _canLoadMore = false
    val canLoadMore: Boolean
        get() = _canLoadMore

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<LandingItem>> = _movies.map { generateLandingItem(it) }

    private val pageFlow = MutableSharedFlow<Int>()

    private val fetchFlow = pageFlow
        .flatMapLatest { page -> fetchListingUseCase(page) }
        .onEach { result ->
            if (result.isSuccess()) {
                result.data?.let { landingPage ->
                    currentPage = landingPage.currentPage
                    _canLoadMore = landingPage.currentPage < landingPage.totalPage
                    when {
                        landingPage.currentPage == 1 -> _movies.postValue(landingPage.movies)
                        landingPage.currentPage > 1 -> {
                            val currentList = _movies.value ?: emptyList()
                            _movies.postValue(currentList.plus(landingPage.movies))
                        }
                    }
                }
            }
        }
        .shareIn(viewModelScope, SharingStarted.Eagerly, 1)

    val fetchError = fetchFlow
        .filter { it.isError() }
        .map { Event(it.throwable ?: UnknownException()) }
        .shareIn(viewModelScope, SharingStarted.Eagerly, 1)
        .asLiveData()

    val isLoading = fetchFlow
        .map { it.isLoading() }
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)
        .asLiveData()

    fun refresh() {
        viewModelScope.launch { pageFlow.emit(1) }
    }

    fun loadMore() {
        viewModelScope.launch {
            if (_canLoadMore) {
                pageFlow.emit(currentPage + 1)
            }
        }
    }

    private fun generateLandingItem(movies: List<Movie>): List<LandingItem> {
        var adsCount = 0
        return movies.foldIndexed(initial = arrayListOf()) { index, items, movie ->
            items.add(MovieItem(movie))
            if (index % 3 == 2) {
                adsCount++
                val ads = Ads(id = adsCount, content = "Advertisement $adsCount")
                items.add(AdsItem(ads))
            }
            items
        }
    }
}