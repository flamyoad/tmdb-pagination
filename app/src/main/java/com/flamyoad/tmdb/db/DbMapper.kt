package com.flamyoad.tmdb.db

import com.flamyoad.tmdb.api.MovieDetailsJson
import com.flamyoad.tmdb.api.MovieJson
import com.flamyoad.tmdb.model.*

object DbMapper {
    fun mapMovieJson(movieJsonList: List<MovieJson>, prevPage: Int?, nextPage: Int?): List<Movie> {
        return movieJsonList.map { movieJson ->
            Movie(
                id = null,
                movieId = movieJson.id,
                title = movieJson.title,
                voteAverage = movieJson.voteAverage,
                posterPath = movieJson.posterPath ?: "",
                releaseDate = movieJson.releaseDate,
                prevPage = prevPage,
                nextPage = nextPage
            )
        }
    }

    fun mapMovieDetailsJson(json: MovieDetailsJson): MovieDetails {
        val genres = json.genres.map {
            Genre(id = it.id, name = it.name ?: "")
        }

        val spokenLanguages = json.spokenLanguages.map {
            SpokenLanguage(englishName = it.english_name ?: "", iso_639_1 = it.iso_639_1 ?: "", name = it.name ?: "")
        }

        val companies = json.productionCompanies.map {
            ProductionCompany(
                id = it.id,
                logoPath = it.logo_path ?: "",
                name = it.name ?: "",
                originCountry = it.origin_country ?: ""
            )
        }

        val countries = json.productionCountries.map {
            ProductionCountry(
                id = it.id,
                logoPath = it.logo_path ?: "",
                name = it.name ?: "",
                originCountry = it.origin_country ?: ""
            )
        }

        return MovieDetails(
            id = json.id,
            homePage = json.homePage ?: "",
            title = json.title ?: "",
            tagline = json.tagline ?: "",
            summary = json.summary ?: "",
            releaseDate = json.releaseDate ?: "",
            runTime = json.runTime ?: 0,
            backdropPath = json.backdropPath ?: "",
            posterPath = json.posterPath ?: "",
            voteAverage = json.voteAverage ?: 0.00,
            genres = genres,
            spokenLanguages = spokenLanguages,
            productionCompanies = companies,
            productionCountries = countries
        )
    }
}