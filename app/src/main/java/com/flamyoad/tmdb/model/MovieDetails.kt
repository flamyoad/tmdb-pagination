package com.flamyoad.tmdb.model

data class MovieDetails(
    val id: Int = -1,
    val homePage: String = "",
    val title: String = "",
    val tagline: String = "",
    val summary: String = "",
    val releaseDate: String = "",
    val runTime: Int,
    val backdropPath: String = "",
    val posterPath: String = "",
    val voteAverage: Double = 0.00,
    val genres: List<Genre> = emptyList(),
    val spokenLanguages: List<SpokenLanguage> = emptyList(),
    val productionCountries: List<ProductionCountry> = emptyList(),
    val productionCompanies: List<ProductionCompany> = emptyList()
) {
    fun getPosterUrl(): String {
        return "https://image.tmdb.org/t/p/w500${this.posterPath}"
    }

    fun getBackdropUrl(): String {
        return "https://image.tmdb.org/t/p/w500${this.backdropPath}"
    }

    fun getUserScore(): String {
        return (this.voteAverage * 10).toString() + "%"
    }

    fun getGenres(): String {
        return genres.joinToString(separator = ", ") {
            it.name
        }
    }

    fun getLanguages(): String {
        return spokenLanguages.joinToString(separator = ", ") {
            it.name
        }
    }

    fun getPlaytime(): String {
        val hours = this.runTime / 60
        val minutes = this.runTime % 60
        return hours.toString() + "h " + minutes.toString() + "m"
    }

    fun getProdCountries(): String {
        return productionCountries.joinToString(separator = ", ") {
            it.name
        }
    }
}


