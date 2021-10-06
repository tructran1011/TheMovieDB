package com.me.themoviedb.presentation.landing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.me.themoviedb.common.EventObserver
import com.me.themoviedb.common.util.addSimpleDivider
import com.me.themoviedb.databinding.FragmentListingBinding
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
            rvMovies.layoutManager = LinearLayoutManager(requireContext())
            rvMovies.addSimpleDivider()
            rvMovies.adapter = MovieAdapter {

            }

            swipeRefreshLayout.setOnRefreshListener {
                viewModel.refresh()
            }
        }
    }

    private fun observeData() {
        viewModel.run {
            movies.observe(viewLifecycleOwner) {
                Timber.d("Movies: ${it.size}")
                getAdapter()?.submitList(it)
            }

            fetchError.observe(viewLifecycleOwner, EventObserver {
                Timber.d("Error: $it")
            })

            isLoading.observe(viewLifecycleOwner) { isLoading ->
                Timber.d("Is Loading: $isLoading")
                binding?.swipeRefreshLayout?.isRefreshing = isLoading
            }
        }
    }

    private fun getAdapter(): MovieAdapter? =
        binding?.rvMovies?.adapter as? MovieAdapter
}