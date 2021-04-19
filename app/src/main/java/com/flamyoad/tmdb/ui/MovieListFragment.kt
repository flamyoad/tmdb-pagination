package com.flamyoad.tmdb.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.flamyoad.tmdb.adapter.MovieListAdapter
import com.flamyoad.tmdb.adapter.MovieListLoadStateAdapter
import com.flamyoad.tmdb.databinding.FragmentMovieListBinding
import com.flamyoad.tmdb.model.Movie
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalPagingApi
class MovieListFragment : Fragment() {
    private lateinit var binding: FragmentMovieListBinding

    private val viewModel: MovieListViewModel by viewModels()

    private val movieAdapter = MovieListAdapter(this::goToMovieDetailsScreen)

    private var gridLayoutManager: GridLayoutManager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMovieList()
    }

    private fun initMovieList() {
        gridLayoutManager = GridLayoutManager(requireContext(), 2)

        movieAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        with(binding) {
            listMovies.apply {
                adapter = movieAdapter
                layoutManager = gridLayoutManager!!
                setHasFixedSize(true)
            }

            (listMovies.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

            swipeRefreshLayout.setOnRefreshListener {
                movieAdapter.refresh()
            }
        }

        movieAdapter.addLoadStateListener { loadState ->
            binding.swipeRefreshLayout.isRefreshing =
                loadState.mediator?.refresh is LoadState.Loading
        }

        lifecycleScope.launch {
            viewModel.nowShowingMovies().collectLatest {
                movieAdapter.submitData(it)
            }
        }
    }

    private fun goToMovieDetailsScreen(movie: Movie) {
        val intent = Intent(requireContext(), MovieDetailsActivity::class.java)
        intent.putExtra(MOVIE_ID, movie.movieId)
        startActivity(intent)
    }

    companion object {
        const val MOVIE_ID = "movie_id"
        const val TAG = "movie_list_fragment"

        fun newInstance(): MovieListFragment {
            return MovieListFragment()
        }
    }
}
