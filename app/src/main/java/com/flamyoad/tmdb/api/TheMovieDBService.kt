package com.flamyoad.tmdb.api

import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBService {
    /**
     * Example request: https://api.themoviedb.org/3/movie/now_playing?api_key=xxxxxxx&language=en-US&page=1
     */
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") pageNumber: Int
    ): NowPlayingMoviesJson

    /**
     * Example request: https://api.themoviedb.org/3/movie/550?api_key=xxxxxxx
     */
    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String
    ): MovieDetailsJson

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"

        fun create(): TheMovieDBService {
            val client = OkHttpClient.Builder().build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TheMovieDBService::class.java)
        }
    }
}