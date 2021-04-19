package com.flamyoad.tmdb.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.flamyoad.tmdb.ApiKey
import com.flamyoad.tmdb.api.MovieDetailsJson
import com.flamyoad.tmdb.api.TheMovieDBService
import com.flamyoad.tmdb.db.DbMapper
import com.flamyoad.tmdb.model.MovieDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val movieApi: TheMovieDBService = TheMovieDBService.create()

    private val movieDetails = MutableLiveData<MovieDetails>()
    fun movieDetails(): LiveData<MovieDetails> = movieDetails

    fun fetchMovieDetails(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val json = movieApi.getMovieDetails(movieId, API_KEY)
            val details = DbMapper.mapMovieDetailsJson(json)

            withContext(Dispatchers.Main) {
                movieDetails.value = details
            }
        }
    }

    companion object {
        private const val API_KEY = ApiKey.value
    }
}