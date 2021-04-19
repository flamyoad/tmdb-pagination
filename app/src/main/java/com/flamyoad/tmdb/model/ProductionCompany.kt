package com.flamyoad.tmdb.model

data class ProductionCompany(
    val id: Int,
    val logoPath: String,
    val name: String,
    val originCountry: String
) {
    fun getLogoUrl(): String {
        return "https://image.tmdb.org/t/p/w500${this.logoPath}"
    }
}