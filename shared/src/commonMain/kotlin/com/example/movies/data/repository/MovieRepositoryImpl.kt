package com.example.movies.data.repository

import com.example.movies.data.mappers.toMovie
import com.example.movies.data.remote.RemoteDataSource
import com.example.movies.domain.model.Movie
import com.example.movies.domain.repository.MovieRepository

internal class MovieRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
) : MovieRepository {
    override suspend fun getMovies(page: Int): List<Movie> {
        return remoteDataSource.getMovies(page = page).results.map { it.toMovie() }
    }

    override suspend fun getMovie(movieId: Int): Movie {
        return remoteDataSource.getMovieId(movieId = movieId).toMovie()
    }

}