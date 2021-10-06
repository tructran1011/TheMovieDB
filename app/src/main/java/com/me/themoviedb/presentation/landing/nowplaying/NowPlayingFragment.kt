package com.me.themoviedb.presentation.landing.nowplaying

import androidx.fragment.app.viewModels
import com.me.themoviedb.presentation.landing.MovieListingFragment
import com.me.themoviedb.presentation.landing.MovieListingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NowPlayingFragment : MovieListingFragment() {

    private val viewModel: NowPlayingViewModel by viewModels()

    override fun createViewModel(): MovieListingViewModel {
        return viewModel
    }

    companion object {
        fun newInstance(): NowPlayingFragment {
            return NowPlayingFragment()
        }
    }
}