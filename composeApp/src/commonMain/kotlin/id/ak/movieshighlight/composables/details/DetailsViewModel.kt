package id.ak.movieshighlight.composables.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.ak.movieshighlight.data.model.remote.MovieDetailsResponse
import id.ak.movieshighlight.data.model.remote.TvDetailsResponse
import id.ak.movieshighlight.data.repository.MovieRepository
import id.ak.movieshighlight.data.repository.WatchlistRepository
import id.ak.movieshighlight.state.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flattenConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val repository: MovieRepository,
    private val watchlistRepository: WatchlistRepository
) : ViewModel() {
    private val isMovie = MutableStateFlow<Boolean?>(null)

    private val _movieState = MutableStateFlow<UiState<MovieDetailsResponse>>(UiState.Idle)
    val movieState = _movieState.asStateFlow()

    private val _tvSeriesState = MutableStateFlow<UiState<TvDetailsResponse>>(UiState.Idle)
    val tvSeriesState = _tvSeriesState.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val isInWatchlist =
        combine(isMovie, _movieState, _tvSeriesState) { isMovie, movieState, tvSeriesState ->
            when (isMovie) {
                true -> (movieState as? UiState.Success<MovieDetailsResponse>)?.data?.id?.let { id ->
                    watchlistRepository.isInWatchlist(id, true)
                }

                false -> (tvSeriesState as? UiState.Success<TvDetailsResponse>)?.data?.id?.let { id ->
                    watchlistRepository.isInWatchlist(id, false)
                }

                null -> flowOf(null)
            } ?: flowOf(null)
        }.flattenConcat()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)


    fun fetchDetails(id: Int, isMovie: Boolean) {
        this.isMovie.value = isMovie

        viewModelScope.launch {
            if (isMovie) {
                _movieState.value = UiState.Loading
                repository.getMovieDetails(id)
                    .onSuccess { _movieState.value = UiState.Success(it) }
                    .onFailure { _movieState.value = UiState.Error(it.message) }
            } else {
                _tvSeriesState.value = UiState.Loading
                repository.getTvSeriesDetails(id)
                    .onSuccess { _tvSeriesState.value = UiState.Success(it) }
                    .onFailure { _tvSeriesState.value = UiState.Error(it.message) }
            }
        }
    }

    fun addToWatchlist(id: Int, posterUrl: String, title: String) {
        viewModelScope.launch {
            when (isMovie.value) {
                true -> watchlistRepository.addMovie(id = id, posterUrl = posterUrl, title = title)
                false -> watchlistRepository.addTvSeries(
                    id = id,
                    posterUrl = posterUrl,
                    title = title
                )

                null -> {}
            }
        }
    }

    fun removeFromWatchlist(id: Int) {
        viewModelScope.launch {
            when (isMovie.value) {
                true -> watchlistRepository.deleteMovie(id)
                false -> watchlistRepository.deleteTvSeries(id)
                null -> {}
            }
        }
    }
}