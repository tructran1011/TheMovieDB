package com.me.themoviedb.presentation.landing.tab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.me.themoviedb.NavGraphDirections
import com.me.themoviedb.common.EventObserver
import com.me.themoviedb.common.util.addSimpleDivider
import com.me.themoviedb.databinding.FragmentListingBinding
import com.me.themoviedb.domain.model.Movie
import com.me.themoviedb.presentation.BaseFragment
import timber.log.Timber

abstract class MovieListingFragment : BaseFragment<FragmentListingBinding>() {

    private val viewModel: MovieListingViewModel
        get() = createViewModel()

    abstract fun createViewModel(): MovieListingViewModel

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentListingBinding {
        return FragmentListingBinding.inflate(inflater, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenResumed {
            viewModel.refresh()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setup()
        observeData()
    }

    private fun setup() {
        binding?.run {
            val linearLayoutManager = LinearLayoutManager(requireContext())

            val landingAdapter = LandingAdapter {
                goToDetails(it)
            }

            rvMovies.layoutManager = linearLayoutManager
            rvMovies.addSimpleDivider()
            rvMovies.adapter = landingAdapter
            rvMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val itemCount = landingAdapter.itemCount
                    if (
                        viewModel.canLoadMore
                        && itemCount > 0
                        && linearLayoutManager.findLastCompletelyVisibleItemPosition() == itemCount - 1
                    ) {
                        viewModel.loadMore()
                    }
                }
            })

            swipeRefreshLayout.setOnRefreshListener {
                viewModel.refresh()
            }
        }
    }

    private fun observeData() {
        viewModel.run {
            landingItems.observe(viewLifecycleOwner) {
                Timber.d("Movies: ${it.size}")
                getAdapter()?.submitList(it)
            }

            fetchError.observe(viewLifecycleOwner, EventObserver {
                handleError(it)
            })

            isLoading.observe(viewLifecycleOwner) { isLoading ->
//                Timber.d("Is Loading: $isLoading")
                binding?.swipeRefreshLayout?.isRefreshing = isLoading
            }
        }
    }

    private fun getAdapter(): LandingAdapter? =
        binding?.rvMovies?.adapter as? LandingAdapter

    private fun goToDetails(movie: Movie) {
        val directions = NavGraphDirections.openDetails(
            movie.id,
            movie.title,
            movie.year
        )
        navigate(directions)
    }
}