package com.flamyoad.tmdb.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val movieId: Int,
    val title: String,
    val voteAverage: Double,
    val posterPath: String,
    val releaseDate: String,
    val prevPage: Int? = null,
    val nextPage: Int? = null
) {

    /**
     * Returns the image link of the poster to be used in Glide (Width: 500px)
     * TMDB API only returns the file path. Thus we have to generate full image URL on our own
     *
     * backdrop_path: The scrim background
     * poster_path:   The main image shown in the website
     */
    fun getPosterUrl(): String {
        return "https://image.tmdb.org/t/p/w500${this.posterPath}"
    }
}