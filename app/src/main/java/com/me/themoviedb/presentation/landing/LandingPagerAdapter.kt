package com.me.themoviedb.presentation.landing

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.me.themoviedb.presentation.landing.tab.nowplaying.NowPlayingFragment
import com.me.themoviedb.presentation.landing.tab.toprated.TopRatedFragment

class LandingPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return LandingScreen.values().size
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            LandingScreen.NowPlaying.ordinal -> NowPlayingFragment.newInstance()
            LandingScreen.TopRated.ordinal -> TopRatedFragment.newInstance()
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
}