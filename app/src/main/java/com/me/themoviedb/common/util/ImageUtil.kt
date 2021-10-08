package com.me.themoviedb.common.util

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.me.themoviedb.R

fun ImageView.load(url: String, @DrawableRes placeholder: Int = R.drawable.placeholder) {
//    Timber.d("Load: $url")
    Glide.with(this)
        .load(url)
        .placeholder(placeholder)
        .error(placeholder)
        .into(this)
}