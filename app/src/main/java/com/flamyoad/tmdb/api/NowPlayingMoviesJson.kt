package com.flamyoad.tmdb.api

import com.google.gson.annotations.SerializedName

data class NowPlayingMoviesJson(
    @SerializedName("maximum") val maximumDate: String,
    @SerializedName("minimum") val minimumDate: String,
    @SerializedName("results") val movieList: List<MovieJson> = emptyList(),
    @SerializedName("page") val currentPage: Int = 0,
    @SerializedName("total_pages") val totalPages: Int = 0,
    @SerializedName("total_results") val totalResults: Int = 0
) {
    fun hasReachedEndOfList(): Boolean {
        return currentPage == totalPages
    }
}