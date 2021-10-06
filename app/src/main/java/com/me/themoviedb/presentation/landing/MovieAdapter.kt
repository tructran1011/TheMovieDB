package com.me.themoviedb.presentation.landing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.me.themoviedb.R
import com.me.themoviedb.databinding.ItemMovieBinding
import com.me.themoviedb.domain.model.Movie

class MovieAdapter(
    private val onItemClick: (Movie) -> Unit
) : ListAdapter<Movie, MovieAdapter.MovieViewHolder>(Movie.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MovieViewHolder(
        private val binding: ItemMovieBinding,
        onItemClick: (Movie) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private var movie: Movie? = null

        init {
            binding.root.setOnClickListener {
                movie?.let { onItemClick(it) }
            }
        }

        fun bind(movie: Movie) {
            this.movie = movie
            binding.run {
                ivImage.setImageResource(R.mipmap.ic_launcher)
                tvTitle.text = movie.title
                tvTime.text = movie.releaseDate
                tvOverview.text = movie.overview
                tvRate.text = movie.voteAverage.toString()
            }
        }
    }
}