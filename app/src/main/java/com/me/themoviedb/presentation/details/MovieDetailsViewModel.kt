package com.me.themoviedb.presentation.details

import androidx.lifecycle.*
import com.me.themoviedb.common.Event
import com.me.themoviedb.common.isError
import com.me.themoviedb.common.isLoading
import com.me.themoviedb.domain.model.MovieCredits
import com.me.themoviedb.domain.model.MovieDetails
import com.me.themoviedb.domain.model.MovieDetailsWithCredits
import com.me.themoviedb.domain.usecase.movie.FetchMovieCreditsUseCase
import com.me.themoviedb.domain.usecase.movie.FetchMovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val fetchMovieDetailsUseCase: FetchMovieDetailsUseCase,
    private val fetchMovieCreditsUseCase: FetchMovieCreditsUseCase,
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: LiveData<Boolean> = _isLoading.asLiveData()

    private val _error = MutableLiveData<Exception>()
    val error: LiveData<Event<Exception>> = _error.map { Event(it) }

    private val _data = MutableSharedFlow<Pair<MovieDetails, MovieCredits>>()
    private val showAllFlow = MutableStateFlow(false)
    val data = combine(
        _data,
        showAllFlow
    ) { (details, credits), showAll ->
        MovieDetailsWithCredits(
            details = details,
            credits = credits,
            showAll = showAll
        )
    }
        .shareIn(viewModelScope, SharingStarted.Eagerly, 1)
        .asLiveData()

    private val idFlow = MutableSharedFlow<Int>()

    private val fetchDetailsFlow = idFlow
        .flatMapLatest { id -> fetchMovieDetailsUseCase(id) }

    private val fetchCreditsFlow = idFlow
        .flatMapLatest { id -> fetchMovieCreditsUseCase(id) }

    private val combineFlow = combine(
        fetchDetailsFlow,
        fetchCreditsFlow
    ) { detailsResult, creditsResult ->
        Pair(detailsResult, creditsResult)
    }
        .onEach { (detailsResult, creditsResult) ->
            val isLoading = detailsResult.isLoading() || creditsResult.isLoading()
            _isLoading.emit(isLoading)

            when {
                detailsResult.isError() || creditsResult.isError() -> {
                    _error.postValue(Exception())
                }

                detailsResult.data != null && creditsResult.data != null -> {
                    _data.emit(Pair(detailsResult.data, creditsResult.data))
                }
            }
        }
        .shareIn(viewModelScope, SharingStarted.Eagerly, 1)

    fun fetch(id: Int) {
        viewModelScope.launch { idFlow.emit(id) }
    }

    fun onShowAllChanged(showAll: Boolean) {
        viewModelScope.launch { showAllFlow.emit(showAll) }
    }
}