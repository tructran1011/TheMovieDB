package com.me.themoviedb.presentation.landing

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.me.themoviedb.R

enum class LandingScreen(
    @StringRes val textResId: Int,
    @DrawableRes val iconResId: Int,
) {
     NowPlaying(R.string.now_playing, R.drawable.ic_now_playing_selector),
     TopRated(R.string.top_rated, R.drawable.ic_top_rated_selector)
}