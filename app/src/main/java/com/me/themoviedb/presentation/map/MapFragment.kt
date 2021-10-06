package com.me.themoviedb.presentation.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.me.themoviedb.common.util.toast
import com.me.themoviedb.databinding.FragmentMapBinding
import com.me.themoviedb.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>() {

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?): FragmentMapBinding {
        return FragmentMapBinding.inflate(inflater, container, false)
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
        val directions = MapFragmentDirections.openLanding()
        navigate(directions)
    }
}