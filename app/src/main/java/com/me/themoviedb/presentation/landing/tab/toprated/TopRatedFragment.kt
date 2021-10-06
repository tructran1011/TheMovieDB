package com.me.themoviedb.presentation.landing.tab.toprated

import androidx.fragment.app.viewModels
import com.me.themoviedb.presentation.landing.tab.MovieListingFragment
import com.me.themoviedb.presentation.landing.tab.MovieListingViewModel
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