package com.me.themoviedb.presentation.landing.tab.nowplaying

import com.me.themoviedb.domain.usecase.movie.FetchNowPlayingUseCase
import com.me.themoviedb.presentation.landing.tab.MovieListingViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NowPlayingViewModel @Inject constructor(
    fetchNowPlayingUseCase: FetchNowPlayingUseCase
) : MovieListingViewModel(fetchNowPlayingUseCase)