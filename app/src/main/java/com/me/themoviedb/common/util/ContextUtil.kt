package com.me.themoviedb.common.util

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.me.themoviedb.R
import timber.log.Timber

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(message: String) {
    try {
        requireContext().toast(message)
    } catch (e: Exception) {
        Timber.e(e)
    }
}

fun Fragment.toast(@StringRes messageId: Int) {
    toast(getString(messageId))
}

fun Fragment.toastGeneralErrorMessage() {
    toast(R.string.general_error_message)
}