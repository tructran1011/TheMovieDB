package com.me.themoviedb.testutil

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot

inline fun <reified T : Any> LiveData<T>.capturesAllValues(block: (List<T>) -> Unit) {
    val observer = mockk<Observer<T>>()
    val slot = slot<T>()
    val capturedValues = arrayListOf<T>()

    every { observer.onChanged(capture(slot)) } answers {
        //store captured value
        capturedValues.add(slot.captured)
    }
    this.observeForever(observer)
    try {
        block(capturedValues)
    } finally {
        removeObserver(observer)
    }
}

fun <T> LiveData<T>.observeForTesting(block: () -> Unit) {
    val observer = Observer<T> { }
    try {
        observeForever(observer)
        block()
    } finally {
        removeObserver(observer)
    }
}

fun List<LiveData<*>>.observeForTesting(block: () -> Unit) {
    val observer = Observer<Any> { }
    try {
        forEach { it.observeForever(observer) }
        block()
    } finally {
        forEach { it.removeObserver(observer) }
    }
}