package com.example.movies.android.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.domain.model.Movie
import com.example.movies.domain.usecase.GetMoviesUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    val getMoviesUseCase: GetMoviesUseCase,
) : ViewModel() {

    var uiState by mutableStateOf(HomeScreenState())
    private var currentPage = 1

    init {
        loadMovies(forceReload = false)
    }

    fun loadMovies(forceReload: Boolean = true) {
        if (uiState.loading) return
        if (forceReload) currentPage = 1
        if (currentPage == 1) uiState = uiState.copy(refreshing = true)

        viewModelScope.launch {
            uiState = uiState.copy(
                loading = true
            )

            try {
                val resultMovies = getMoviesUseCase(page = currentPage)
                val movies = if (currentPage == 1) resultMovies else uiState.movies + resultMovies

                currentPage += 1
                uiState = uiState.copy(
                    loading = false,
                    refreshing = false,
                    loadFinished = resultMovies.isEmpty(),
                    movies = movies
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    loading = false,
                    refreshing = false,
                    loadFinished = true,
                    errorMessage = "Couldn't load movies ${e.localizedMessage}"
                )
            }
        }
    }
}

data class HomeScreenState(
    var loading: Boolean = false,
    val refreshing: Boolean = false,
    var movies: List<Movie> = listOf(),
    val errorMessage: String? = null,
    val loadFinished: Boolean = false,
)