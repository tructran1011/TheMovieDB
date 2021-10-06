package com.me.themoviedb.presentation.landing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.me.themoviedb.R
import com.me.themoviedb.databinding.ItemAdsBinding
import com.me.themoviedb.databinding.ItemMovieBinding
import com.me.themoviedb.domain.model.Ads
import com.me.themoviedb.domain.model.Movie

open class LandingViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    open fun displayMovie(movieItem: MovieItem) {}

    open fun displayAds(adsItem: AdsItem) {}

    class Factory(
        private val inflater: LayoutInflater,
        private val viewGroup: ViewGroup?,
        private val onMovieClick: (Movie) -> Unit,
    ) {
        fun create(viewType: Int): LandingViewHolder {
            val view = inflater.inflate(viewType, viewGroup, false)
            return when (viewType) {
                MovieViewHolder.LAYOUT_ID -> MovieViewHolder(view, onMovieClick)
                AdsViewHolder.LAYOUT_ID -> AdsViewHolder(view)
                else -> throw IllegalArgumentException("Illegal viewType: $viewType")
            }
        }
    }
}

class MovieViewHolder(
    view: View,
    onItemClick: (Movie) -> Unit
) : LandingViewHolder(view) {

    private val binding = ItemMovieBinding.bind(view)
    private var movie: Movie? = null

    init {
        binding.root.setOnClickListener {
            movie?.let { onItemClick(it) }
        }
    }

    override fun displayMovie(movieItem: MovieItem) {
        val movie = movieItem.movie
        this.movie = movie
        binding.run {
            ivImage.setImageResource(R.mipmap.ic_launcher)
            tvTitle.text = movie.title
            tvTime.text = movie.releaseDate
            tvOverview.text = movie.overview
            tvRate.text = movie.voteAverage.toString()
        }
    }

    companion object {
        const val LAYOUT_ID = R.layout.item_movie
    }
}

class AdsViewHolder(
    view: View
) : LandingViewHolder(view) {

    private val binding = ItemAdsBinding.bind(view)

    override fun displayAds(adsItem: AdsItem) {
        binding.tvContent.text = adsItem.ads.content
    }

    companion object {
        const val LAYOUT_ID = R.layout.item_ads
    }
}