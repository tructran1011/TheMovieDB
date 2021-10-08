package com.me.themoviedb.presentation.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.me.themoviedb.databinding.FragmentSplashBinding
import com.me.themoviedb.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    private val viewModel: SplashViewModel by viewModels()

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSplashBinding {
        return FragmentSplashBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()

        viewModel.startTimeout()
    }

    private fun observeData() {
        viewModel.run {
            isLoading.observe(viewLifecycleOwner) { isLoading ->
                binding?.pbLoading?.isVisible = isLoading
            }

            timeout.observe(viewLifecycleOwner) {
                goToNextScreen()
            }
        }
    }

    private fun goToNextScreen() {
        val directions = SplashFragmentDirections.openMap()
        navigate(directions)
    }
}