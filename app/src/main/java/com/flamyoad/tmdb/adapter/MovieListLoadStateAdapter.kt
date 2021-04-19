package com.flamyoad.tmdb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.flamyoad.tmdb.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

class MovieListLoadStateAdapter(private val retryAction: () -> Unit) :
    LoadStateAdapter<MovieLoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): MovieLoadStateViewHolder {
        return MovieLoadStateViewHolder.create(parent, retryAction)
    }

    override fun onBindViewHolder(holder: MovieLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }
}

class MovieLoadStateViewHolder(itemView: View, retryAction: () -> Unit) :
    RecyclerView.ViewHolder(itemView) {
    private val btnRetry: MaterialButton = itemView.findViewById(R.id.btnRetry)
    private val txtErrorMessage: MaterialTextView = itemView.findViewById(R.id.txtErrorMessage)
    private val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)

    init {
        btnRetry.setOnClickListener { retryAction() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            txtErrorMessage.text = loadState.error.localizedMessage
        }

        progressBar.isVisible = loadState is LoadState.Loading
        btnRetry.isVisible = loadState is LoadState.Error
        txtErrorMessage.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retryAction: () -> Unit): MovieLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_list_load_state_footer, parent, false)
            return MovieLoadStateViewHolder(view, retryAction)
        }
    }
}