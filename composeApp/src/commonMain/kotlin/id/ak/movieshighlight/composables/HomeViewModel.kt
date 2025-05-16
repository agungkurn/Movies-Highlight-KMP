package id.ak.movieshighlight.composables

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.ak.movieshighlight.data.model.DiscoverMovieModel
import id.ak.movieshighlight.data.model.DiscoverTvModel
import id.ak.movieshighlight.data.repository.MovieRepository
import id.ak.movieshighlight.state.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MovieRepository) : ViewModel() {
    private val _movies = MutableStateFlow<UiState<List<DiscoverMovieModel>>>(UiState.Idle)
    val movies = _movies.asStateFlow()

    private val _tvSeries = MutableStateFlow<UiState<List<DiscoverTvModel>>>(UiState.Idle)
    val tvSeries = _tvSeries.asStateFlow()

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