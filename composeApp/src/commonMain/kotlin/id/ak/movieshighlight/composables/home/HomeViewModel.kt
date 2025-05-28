package id.ak.movieshighlight.composables.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.ak.movieshighlight.data.model.local.ThemeMode
import id.ak.movieshighlight.data.model.remote.DiscoverMovieModel
import id.ak.movieshighlight.data.model.remote.DiscoverTvModel
import id.ak.movieshighlight.data.repository.MovieRepository
import id.ak.movieshighlight.data.repository.ThemeRepository
import id.ak.movieshighlight.state.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: MovieRepository,
    private val themeRepository: ThemeRepository
) : ViewModel() {
    val currentTheme = themeRepository.currentTheme
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ThemeMode.System)

    private val _movies = MutableStateFlow<UiState<List<DiscoverMovieModel>>>(UiState.Idle)
    val movies = _movies.asStateFlow()

    private val _tvSeries = MutableStateFlow<UiState<List<DiscoverTvModel>>>(UiState.Idle)
    val tvSeries = _tvSeries.asStateFlow()

    fun toggleTheme() {
        viewModelScope.launch {
            themeRepository.toggleTheme()
        }
    }

    fun fetchMovies() {
        viewModelScope.launch {
            _movies.value = UiState.Loading

            repository.getMovies()
                .onSuccess {
                    _movies.value = UiState.Success(it)
                }
                .onFailure {
                    _movies.value = UiState.Error(it.message)
                    delay(3000)
                    _movies.value = UiState.Idle
                }
        }
    }

    fun fetchTvSeries() {
        viewModelScope.launch {
            _tvSeries.value = UiState.Loading

            repository.getTvSeries()
                .onSuccess {
                    _tvSeries.value = UiState.Success(it)
                }
                .onFailure {
                    _tvSeries.value = UiState.Error(it.message)
                    delay(3000)
                    _tvSeries.value = UiState.Idle
                }
        }
    }
}