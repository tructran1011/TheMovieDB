package com.me.themoviedb.presentation.landing.tab

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.me.themoviedb.R
import com.me.themoviedb.common.util.getYear
import com.me.themoviedb.common.util.loadCenterCrop
import com.me.themoviedb.databinding.ItemAdsBinding
import com.me.themoviedb.databinding.ItemMovieBinding
import com.me.themoviedb.domain.model.Movie
import com.me.themoviedb.presentation.landing.AdsItem
import com.me.themoviedb.presentation.landing.MovieItem

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
            ivImage.loadCenterCrop(movie.image)
            tvTitle.text = movie.title
            tvTime.text = movie.year
            tvOverview.text = movie.overview
            tvRate.text = binding.root.context.getString(
                R.string.vote_with_format,
                movie.voteAverage,
                movie.voteCount
            )
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