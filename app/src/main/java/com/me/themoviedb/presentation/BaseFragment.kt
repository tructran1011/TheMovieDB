package com.me.themoviedb.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T: ViewBinding> : Fragment() {

    protected var binding: T? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflateView(inflater, container)
        return binding?.root
    }

    abstract fun inflateView(inflater: LayoutInflater, container: ViewGroup?): T

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    protected fun navigate(directions: NavDirections) {
        findNavController().navigate(directions)
    }
}