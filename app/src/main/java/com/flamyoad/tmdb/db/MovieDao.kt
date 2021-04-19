package com.flamyoad.tmdb.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.flamyoad.tmdb.model.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    fun getAll(): PagingSource<Int, Movie>

    @Insert
    fun insert(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movieList: List<Movie>)

    @Query("DELETE FROM movies")
    fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM movies WHERE movieId = :movieId)")
    fun exists(movieId: Int): Boolean
}