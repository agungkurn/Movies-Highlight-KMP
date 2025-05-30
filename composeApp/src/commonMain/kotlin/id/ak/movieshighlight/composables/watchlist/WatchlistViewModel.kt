package id.ak.movieshighlight.composables.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.ak.movieshighlight.data.repository.WatchlistRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class WatchlistViewModel(watchlistRepository: WatchlistRepository) : ViewModel() {
    val movies = watchlistRepository.movies
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    val tvSeries = watchlistRepository.tvSeries
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
}