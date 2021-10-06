package com.me.themoviedb.presentation.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    private val _timeout = MutableLiveData<Unit>()
    val timeout: LiveData<Unit> = _timeout

    fun startTimeout() {
        viewModelScope.launch {
            delay(SPLASH_TIMEOUT_IN_MILLIS)
            _timeout.postValue(Unit)
        }
    }

    companion object {
        const val SPLASH_TIMEOUT_IN_MILLIS = 3_000L
    }
}