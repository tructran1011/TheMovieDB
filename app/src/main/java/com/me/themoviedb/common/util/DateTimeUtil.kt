package com.me.themoviedb.common.util

import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

fun String.getYear(): String = convertFormat("yyyy-MM-dd", "yyyy")

private fun String.convertFormat(oldPattern: String, newPattern: String): String = try {
    val oldSdf = SimpleDateFormat(oldPattern, Locale.getDefault())
    val newSdf = SimpleDateFormat(newPattern, Locale.getDefault())
    val date = oldSdf.parse(this)

    if (date != null) {
        newSdf.format(date)
    } else {
        ""
    }
} catch (e: Exception) {
    Timber.e(e)
    ""
}

fun Int.minutesToDuration(): String {
    val hour = this / 60
    val mins = this % 60
    return if (mins != 0) {
        "${hour}h ${mins}m"
    } else {
        "${hour}h"
    }
}