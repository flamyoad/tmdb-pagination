package com.flamyoad.tmdb.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.flamyoad.tmdb.R
import com.flamyoad.tmdb.model.Movie
import com.google.android.material.textview.MaterialTextView

class MovieListAdapter(private val onItemClick: (Movie) -> Unit) :
    PagingDataAdapter<Movie, MovieListAdapter.MovieViewHolder>(MOVIE_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_list_item, parent, false)

        val holder = MovieViewHolder(view)

        holder.itemView.setOnClickListener {
            val movie = getItem(holder.bindingAdapterPosition) ?: return@setOnClickListener
            onItemClick(movie)
        }
        return holder
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position) ?: return
        holder.bind(movie)
    }

    override fun onViewRecycled(holder: MovieViewHolder) {
        super.onViewRecycled(holder)
        holder.recycle()
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgMovie: AppCompatImageView = itemView.findViewById(R.id.imgMovie)
        private val txtTitle: MaterialTextView = itemView.findViewById(R.id.txtTitle)
        private val txtDate: MaterialTextView = itemView.findViewById(R.id.txtDate)

        fun bind(movie: Movie) {
            val loadingIndicator = CircularProgressDrawable(itemView.context).apply {
                setColorSchemeColors(Color.BLACK)
                centerRadius = 50f
                strokeWidth = 5f
            }
            loadingIndicator.start()

            Glide.with(itemView.context)
                .load(movie.getPosterUrl())
                .placeholder(loadingIndicator)
                .into(imgMovie)

            txtTitle.text = movie.title
            txtDate.text = movie.releaseDate
        }

        fun recycle() {
            Glide.with(itemView.context).clear(imgMovie)
        }
    }

    companion object {
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }
}

