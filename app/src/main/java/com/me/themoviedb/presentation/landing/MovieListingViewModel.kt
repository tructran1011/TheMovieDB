package com.me.themoviedb.presentation.landing

import androidx.lifecycle.*
import com.me.themoviedb.common.*
import com.me.themoviedb.domain.model.LandingPage
import com.me.themoviedb.domain.model.Movie
import com.me.themoviedb.domain.usecase.FlowUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

open class MovieListingViewModel constructor(
    private val fetchListingUseCase: FlowUseCase<Int, LandingPage>,
) : ViewModel() {

    private var currentPage = 0
    private var canLoadMore = false
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    private val pageFlow = MutableSharedFlow<Int>()

    private val fetchFlow = pageFlow
        .flatMapLatest { page -> fetchListingUseCase(page) }
        .onEach { result ->
            if (result.isSuccess()) {
                result.data?.let { landingPage ->
                    currentPage = landingPage.currentPage
                    canLoadMore = landingPage.currentPage < landingPage.totalPage
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
        viewModelScope.launch { pageFlow.emit(currentPage + 1) }
    }
}