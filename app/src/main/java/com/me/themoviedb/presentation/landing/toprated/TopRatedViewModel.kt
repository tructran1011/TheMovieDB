package com.me.themoviedb.presentation.landing.toprated

import com.me.themoviedb.domain.usecase.movie.FetchTopRatedUseCase
import com.me.themoviedb.presentation.landing.MovieListingViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopRatedViewModel @Inject constructor(
    fetchTopRatedUseCase: FetchTopRatedUseCase
) : MovieListingViewModel(fetchTopRatedUseCase)