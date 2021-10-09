package com.me.themoviedb.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.me.themoviedb.R
import com.me.themoviedb.common.util.toast
import com.me.themoviedb.common.util.toastGeneralErrorMessage
import com.me.themoviedb.presentation.error.AppError
import timber.log.Timber

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

    protected fun handleError(throwable: Throwable) {
        Timber.e(throwable)
        val error = AppError.Factory.create(throwable)
        when (error) {
            AppError.UnknownError -> toastGeneralErrorMessage()
            AppError.NetworkError -> toast(R.string.network_error_message)
            is AppError.ServerError -> toast(R.string.server_error_message)
        }
    }
}