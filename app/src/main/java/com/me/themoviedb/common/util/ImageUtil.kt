package com.me.themoviedb.common.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import timber.log.Timber

fun ImageView.loadCenterCrop(url: String) {
    Timber.d("Load: $url")
    Glide.with(this)
        .load(url)
        .into(this)
}