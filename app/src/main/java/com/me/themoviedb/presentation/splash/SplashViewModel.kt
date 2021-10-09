package com.me.themoviedb.presentation.splash

import androidx.lifecycle.*
import com.me.themoviedb.common.isLoading
import com.me.themoviedb.domain.usecase.configuration.FetchConfigurationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val fetchConfigurationUseCase: FetchConfigurationUseCase,
) : ViewModel() {

    private val _timeout = MutableLiveData<Unit>()
    val timeout: LiveData<Unit> = _timeout

    private val _isLoading = MutableStateFlow(false)
    val isLoading: LiveData<Boolean> = _isLoading.asLiveData()

    private val fetchFlow = MutableSharedFlow<Unit>()
    private val resultFlow = fetchFlow
        .flatMapLatest { fetchConfigurationUseCase(Unit) }

    init {
        viewModelScope.launch {
            resultFlow.collect {
                _isLoading.emit(it.isLoading())
            }
        }
    }

    fun startTimeout() {
        viewModelScope.launch {
            fetchFlow.emit(Unit)
            delay(SPLASH_TIMEOUT_IN_MILLIS)
            _timeout.postValue(Unit)
        }
    }

    companion object {
        const val SPLASH_TIMEOUT_IN_MILLIS = 3_000L
    }
}