package com.flamyoad.tmdb.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.flamyoad.tmdb.api.TheMovieDBService
import com.flamyoad.tmdb.db.AppDatabase
import com.flamyoad.tmdb.db.DbMapper
import com.flamyoad.tmdb.model.Movie
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_NUMBER = 1
private const val DEFAULT_LANG = "en-US"

@ExperimentalPagingApi
class MovieDBMediator(
    private val apiKey: String,
    private val service: TheMovieDBService,
    private val db: AppDatabase
) : RemoteMediator<Int, Movie>() {

//     In cases where we don't mind showing out-of-date,
//     cached offline data, we can return SKIP_INITIAL_REFRESH instead to prevent paging
//     triggering remote refresh.
    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {
        val lastMovie = state.lastItemOrNull()

        val pageNumber = when (loadType) {
            LoadType.REFRESH -> STARTING_PAGE_NUMBER

            // We are not adding items to the start of the list. So we just return MediatorResult.Success(true)
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)

            LoadType.APPEND -> {
                if (lastMovie == null) {
                    return MediatorResult.Success(endOfPaginationReached = false)
                } else if (lastMovie.nextPage == null) {
                    return MediatorResult.Success(endOfPaginationReached = true)
                } else {
                    lastMovie.nextPage
                }
            }
        }

        try {
            val apiResponse = service.getNowPlayingMovies(apiKey, DEFAULT_LANG, pageNumber)
            val endOfPaginationReached = apiResponse.hasReachedEndOfList()

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.movieDao().deleteAll()
                }
                val prevPage = if (pageNumber == STARTING_PAGE_NUMBER) null else pageNumber - 1
                val nextPage = if (endOfPaginationReached) null else pageNumber + 1

                val movieList = DbMapper.mapMovieJson(apiResponse.movieList, prevPage, nextPage)
                db.movieDao().insertAll(movieList)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }
}