package com.flamyoad.tmdb.api

import com.google.gson.annotations.SerializedName

data class MovieJson(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String = "",
    @SerializedName("vote_average") val voteAverage: Double = 0.00,
    @SerializedName("poster_path") val posterPath: String? = "",
    @SerializedName("release_date") val releaseDate: String = "")