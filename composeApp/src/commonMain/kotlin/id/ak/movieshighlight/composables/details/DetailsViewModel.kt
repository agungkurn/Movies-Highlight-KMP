package id.ak.movieshighlight.composables.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.ak.movieshighlight.data.model.remote.MovieDetailsResponse
import id.ak.movieshighlight.data.model.remote.TvDetailsResponse
import id.ak.movieshighlight.data.repository.MovieRepository
import id.ak.movieshighlight.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(private val repository: MovieRepository) : ViewModel() {
    private val _movie = MutableStateFlow<UiState<MovieDetailsResponse>>(UiState.Idle)
    val movie = _movie.asStateFlow()

    private val _tvSeries = MutableStateFlow<UiState<TvDetailsResponse>>(UiState.Idle)
    val tvSeries = _tvSeries.asStateFlow()

    fun fetchDetails(id: Int, isMovie: Boolean) {
        viewModelScope.launch {
            if (isMovie) {
                _movie.value = UiState.Loading
                repository.getMovieDetails(id)
                    .onSuccess { _movie.value = UiState.Success(it) }
                    .onFailure { _movie.value = UiState.Error(it.message) }
            } else {
                _tvSeries.value = UiState.Loading
                repository.getTvSeriesDetails(id)
                    .onSuccess { _tvSeries.value = UiState.Success(it) }
                    .onFailure { _tvSeries.value = UiState.Error(it.message) }
            }
        }
    }
}