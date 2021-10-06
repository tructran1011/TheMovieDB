package com.me.themoviedb.presentation.landing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.me.themoviedb.databinding.FragmentLandingBinding
import com.me.themoviedb.databinding.ItemTabBinding
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
            vpLanding.offscreenPageLimit = LandingScreen.values().size
            vpLanding.adapter = LandingPagerAdapter(this@LandingFragment)


            TabLayoutMediator(tlBottom, vpLanding, true, true) { tab, position ->
                val inflater = LayoutInflater.from(requireContext())
                val tabViewBinding = ItemTabBinding.inflate(inflater)
                val landingScreen = LandingScreen.values()[position]

                tabViewBinding.tvText.setText(landingScreen.textResId)
                tabViewBinding.ivIcon.setImageResource(landingScreen.iconResId)

                tab.customView = tabViewBinding.root
            }.attach()
        }
    }
}