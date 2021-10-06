package com.me.themoviedb.presentation.landing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.me.themoviedb.databinding.FragmentLandingBinding
import com.me.themoviedb.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LandingFragment : BaseFragment<FragmentLandingBinding>() {

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLandingBinding {
        return FragmentLandingBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setup()
    }

    private fun setup() {
        binding?.run {
            btnNext.setOnClickListener {
                goToNextScreen()
            }
        }
    }

    private fun goToNextScreen() {
        val directions =
            LandingFragmentDirections.openDetails("Test ID")
        navigate(directions)
    }
}