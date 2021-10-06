package com.me.themoviedb.presentation.landing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.me.themoviedb.common.EventObserver
import com.me.themoviedb.common.util.addSimpleDivider
import com.me.themoviedb.databinding.FragmentLandingBinding
import com.me.themoviedb.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LandingFragment : BaseFragment<FragmentLandingBinding>() {

    private val viewModel: LandingViewModel by viewModels()

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLandingBinding {
        return FragmentLandingBinding.inflate(inflater, container, false)
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

            isLoading.observe(viewLifecycleOwner) {
                Timber.d("Is Loading: $it")
            }
        }
    }

    private fun getAdapter(): MovieAdapter? =
        binding?.rvMovies?.adapter as? MovieAdapter
}