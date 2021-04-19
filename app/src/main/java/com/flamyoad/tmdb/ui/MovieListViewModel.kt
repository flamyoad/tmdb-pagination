package com.flamyoad.tmdb.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.flamyoad.tmdb.ApiKey
import com.flamyoad.tmdb.api.TheMovieDBService
import com.flamyoad.tmdb.db.AppDatabase
import com.flamyoad.tmdb.model.Movie
import com.flamyoad.tmdb.paging.MovieDBMediator
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
class MovieListViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application)
    private val movieApi: TheMovieDBService = TheMovieDBService.create()

    private var nowShowingMovies: Flow<PagingData<Movie>>? = null
    fun nowShowingMovies() = nowShowingMovies!!

    init {
        nowShowingMovies = Pager(
            config = PagingConfig(pageSize = PAGINATION_SIZE, enablePlaceholders = true),
            remoteMediator = MovieDBMediator(API_KEY, movieApi, db),
            pagingSourceFactory = { db.movieDao().getAll() }
        ).flow.cachedIn(viewModelScope)
    }

    companion object {
        private const val PAGINATION_SIZE = 20
        private const val API_KEY = ApiKey.value
    }
}