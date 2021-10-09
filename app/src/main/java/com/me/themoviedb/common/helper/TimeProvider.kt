package com.me.themoviedb.common.helper

import javax.inject.Inject

interface TimeProvider {
    fun getCurrentTime(): Long
}

class DefaultTimeProvider @Inject constructor() : TimeProvider {
    override fun getCurrentTime(): Long {
        return System.currentTimeMillis()
    }
}