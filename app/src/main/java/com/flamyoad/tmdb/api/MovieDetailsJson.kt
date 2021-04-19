package com.flamyoad.tmdb.api

import com.google.gson.annotations.SerializedName

data class MovieDetailsJson(
    @SerializedName("id") val id: Int = -1,
    @SerializedName("home_page") val homePage: String? = "",
    @SerializedName("title") val title: String? = "",
    @SerializedName("overview") val summary: String? = "",
    @SerializedName("tagline") val tagline: String? = "",
    @SerializedName("release_date") val releaseDate: String? = "",
    @SerializedName("runtime") val runTime: Int? = 0,
    @SerializedName("backdrop_path") val backdropPath: String? = "",
    @SerializedName("poster_path") val posterPath: String? = "",
    @SerializedName("vote_average") val voteAverage: Double? = 0.00,
    @SerializedName("genres") val genres: List<GenreJson> = emptyList(),
    @SerializedName("spoken_languages") val spokenLanguages: List<SpokenLanguageJson> = emptyList(),
    @SerializedName("production_countries") val productionCountries: List<ProductionCountryJson> = emptyList(),
    @SerializedName("production_companies") val productionCompanies: List<ProductionCompanyJson> = emptyList()
)

data class GenreJson(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?
)

data class SpokenLanguageJson(
    @SerializedName("english_name") val english_name: String?,
    @SerializedName("iso_639_1") val iso_639_1: String?,
    @SerializedName("name") val name: String?
)

data class ProductionCountryJson(
    @SerializedName("id") val id: Int,
    @SerializedName("logo_path") val logo_path: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("origin_country") val origin_country: String?
)

data class ProductionCompanyJson(
    @SerializedName("id") val id: Int,
    @SerializedName("logo_path") val logo_path: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("origin_country") val origin_country: String?
)

