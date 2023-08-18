package com.example.movies.data.remote

import com.example.movies.util.Dispatcher
import kotlinx.coroutines.withContext

internal class RemoteDataSource(
    private val apiService: MovieService,
    private val dispatcher: Dispatcher,
) {

    suspend fun getMovies(page: Int) = withContext(dispatcher.io) {
        apiService.getMovies(page)
    }

    suspend fun getMovieId(movieId: Int) = withContext(dispatcher.io) {
        apiService.getMovie(movieId = movieId)
    }

}
