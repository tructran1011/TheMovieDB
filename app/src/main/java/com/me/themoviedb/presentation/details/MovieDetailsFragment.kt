package com.me.themoviedb.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.me.themoviedb.R
import com.me.themoviedb.common.EventObserver
import com.me.themoviedb.common.util.toast
import com.me.themoviedb.databinding.FragmentMovieDetailsBinding
import com.me.themoviedb.domain.model.MovieDetailsWithCredits
import com.me.themoviedb.presentation.BaseFragment
import com.me.themoviedb.presentation.details.adapter.MovieDetailsAdapter
import com.me.themoviedb.presentation.details.adapter.item.*
import com.me.themoviedb.presentation.details.adapter.viewholder.MemberViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.min

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

            swipeRefreshLayout.setOnRefreshListener { viewModel.fetch(args.id) }

            val adapter = MovieDetailsAdapter(object : MovieDetailsAdapter.ItemInteractListener {
                override fun onShowAllClick(showAll: Boolean) {
                    viewModel.onShowAllChanged(!showAll)
                }
            })

            val layoutManager = GridLayoutManager(
                requireContext(),
                4
            ).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if (adapter.getItemViewType(position) == MemberViewHolder.LAYOUT_ID) {
                            1
                        } else {
                            4
                        }
                    }
                }
            }
            rvDetails.layoutManager = layoutManager
            rvDetails.adapter = adapter
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

            data.observe(viewLifecycleOwner) { detailsWithCredits ->
                binding?.run {
                    rvDetails.isVisible = true
//                    Timber.d("Details: $details")
//                    Timber.d("Credits: $credits")
                    displayData(detailsWithCredits)
                }
            }
        }
    }

    private fun displayData(detailsWithCredits: MovieDetailsWithCredits) {
        val details = detailsWithCredits.details
        val credits = detailsWithCredits.credits
        val showAll = detailsWithCredits.showAll
        binding?.run {
            val poster = PosterItem(details.image)
            val title = TitleItem(getString(R.string.movie_name_with_year, details.title, details.year))
            val info = InfoItem(
                getString(
                    R.string.movie_details_info,
                    details.voteAvg,
                    details.duration,
                    details.genres.joinToString()
                )
            )
            val overviewItem = OverviewItem(details.overview)

            val castAndCrewsLabel = CastAndCrewsLabelItem(
                getString(R.string.cast_n_crews),
                showAll,
            )

            val members = when {
                credits.members.isEmpty() -> {
                    emptyList()
                }
                showAll -> {
                    credits.members
                }
                else -> {
                    val subSize = min(4, credits.members.size)
                    credits.members.subList(0, subSize)
                }
            }
                .map { MemberItem(it) }

            val director = TitleItem(
                getString(
                    R.string.director_with_format,
                    credits.displayDirectorNames
                ),
                needTopDivider = members.isNotEmpty(), // If have members list, need to show top divider
                needBotDivider = false,
            )

            val list = arrayListOf<MovieDetailsItem>()
            list.add(poster)
            list.add(title)
            list.add(info)
            list.add(overviewItem)
            if (members.isNotEmpty()) {
                list.add(castAndCrewsLabel)
                list.addAll(members)
            }

            list.add(director)

            getAdapter()?.submitList(list)
        }
    }

    private fun getAdapter() =
        binding?.rvDetails?.adapter as? MovieDetailsAdapter
}