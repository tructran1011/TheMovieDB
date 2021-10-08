package com.me.themoviedb.presentation.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.me.themoviedb.common.helper.TimeProvider
import com.me.themoviedb.common.isLoading
import com.me.themoviedb.domain.usecase.configuration.FetchConfigurationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val fetchConfigurationUseCase: FetchConfigurationUseCase,
    private val timeProvider: TimeProvider
) : ViewModel() {

    private var startTime = 0L

    private val _timeout = MutableLiveData<Unit>()
    val timeout: LiveData<Unit> = _timeout

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val fetchFlow = MutableSharedFlow<Unit>()
    private val resultFlow = fetchFlow
        .flatMapLatest { fetchConfigurationUseCase(Unit) }

    init {
        viewModelScope.launch {
            resultFlow.collect {
                _isLoading.postValue(it.isLoading())

                if (!it.isLoading()) {
                    val diff = timeProvider.getCurrentTime() - startTime
                    if (diff < SPLASH_TIMEOUT_IN_MILLIS) {
                        delay(SPLASH_TIMEOUT_IN_MILLIS - diff)
                    }
                    _timeout.postValue(Unit)
                }
            }
        }
    }

    fun startTimeout() {
        viewModelScope.launch {
            startTime = timeProvider.getCurrentTime()
            fetchFlow.emit(Unit)
        }
    }

    companion object {
        const val SPLASH_TIMEOUT_IN_MILLIS = 3_000L
    }
}