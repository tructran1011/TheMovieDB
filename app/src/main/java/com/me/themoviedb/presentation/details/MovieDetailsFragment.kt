package com.me.themoviedb.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.me.themoviedb.R
import com.me.themoviedb.common.EventObserver
import com.me.themoviedb.common.util.load
import com.me.themoviedb.common.util.toast
import com.me.themoviedb.databinding.FragmentMovieDetailsBinding
import com.me.themoviedb.domain.model.MovieCredits
import com.me.themoviedb.domain.model.MovieDetails
import com.me.themoviedb.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding>() {

    private val args by navArgs<MovieDetailsFragmentArgs>()
    private val viewModel: MovieDetailsViewModel by viewModels()

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMovieDetailsBinding {
        return FragmentMovieDetailsBinding.inflate(inflater, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenResumed { viewModel.fetch(args.id) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setup()

        observeData()
    }

    private fun setup() {
        binding?.run {
            tvScreenTitle.text = getString(
                R.string.movie_name_with_year,
                args.title,
                args.year
            )
        }
    }

    private fun observeData() {
        viewModel.run {
            isLoading.observe(viewLifecycleOwner) { isLoading ->
                binding?.swipeRefreshLayout?.isRefreshing = isLoading
            }

            error.observe(viewLifecycleOwner, EventObserver {
                toast("Something went wrong. Please try again later.")
            })

            data.observe(viewLifecycleOwner) { (details, credits) ->
                binding?.run {
                    swipeRefreshLayout.isVisible = true
                    Timber.d("Details: $details")
                    Timber.d("Credits: $credits")
                    displayData(details, credits)
                }
            }
        }
    }

    private fun displayData(details: MovieDetails, credits: MovieCredits) {
        binding?.run {
            ivPoster.load(details.image)
            tvTitle.text = getString(R.string.movie_name_with_year, details.title, details.year)
            tvDescription.text = getString(
                R.string.movie_details_info,
                details.voteAvg,
                details.duration,
                details.genres.joinToString()
            )
            tvOverview.text = details.overview
            tvDirector.text = getString(R.string.director_with_format, credits.displayDirectorNames)

        }
    }
}