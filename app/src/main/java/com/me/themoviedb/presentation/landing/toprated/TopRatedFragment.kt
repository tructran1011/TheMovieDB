package com.me.themoviedb.presentation.landing.toprated

import androidx.fragment.app.viewModels
import com.me.themoviedb.presentation.landing.MovieListingFragment
import com.me.themoviedb.presentation.landing.MovieListingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopRatedFragment : MovieListingFragment() {

    private val viewModel: TopRatedViewModel by viewModels()

    override fun createViewModel(): MovieListingViewModel {
        return viewModel
    }

    companion object {
        fun newInstance(): TopRatedFragment {
            return TopRatedFragment()
        }
    }
}