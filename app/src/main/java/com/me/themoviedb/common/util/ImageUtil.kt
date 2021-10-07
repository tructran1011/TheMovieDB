package com.me.themoviedb.common.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.me.themoviedb.R
import timber.log.Timber

fun ImageView.load(url: String) {
    Timber.d("Load: $url")
    Glide.with(this)
        .load(url)
        .placeholder(R.drawable.placeholder)
        .error(R.drawable.placeholder)
        .into(this)
}