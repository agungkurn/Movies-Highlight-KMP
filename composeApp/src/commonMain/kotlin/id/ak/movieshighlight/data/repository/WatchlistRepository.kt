package id.ak.movieshighlight.data.repository

import id.ak.movieshighlight.data.local.WatchlistDataSource
import id.ak.movieshighlight.data.model.local.WatchlistMovieEntity
import id.ak.movieshighlight.data.model.local.WatchlistTvShowEntity
import kotlinx.coroutines.flow.Flow

interface WatchlistRepository {
    val movies: Flow<List<WatchlistMovieEntity>>
    val tvSeries: Flow<List<WatchlistTvShowEntity>>

    suspend fun isInWatchlist(id: Int, isMovie: Boolean): Flow<Boolean>

    suspend fun addMovie(id: Int, posterUrl: String, title: String)

    suspend fun addTvSeries(id: Int, posterUrl: String, title: String)

    suspend fun deleteMovie(id: Int)

    suspend fun deleteTvSeries(id: Int)
}

class WatchlistRepositoryImpl(private val watchlistDataSource: WatchlistDataSource) :
    WatchlistRepository {
    override val movies = watchlistDataSource.movies
    override val tvSeries = watchlistDataSource.tvSeries

    override suspend fun isInWatchlist(id: Int, isMovie: Boolean) = if (isMovie) {
        watchlistDataSource.isMovieInWatchlist(id)
    } else {
        watchlistDataSource.isTvShowInWatchlist(id)
    }

    override suspend fun addMovie(id: Int, posterUrl: String, title: String) {
        WatchlistMovieEntity(id = id, posterUrl = posterUrl, title = title).also {
            watchlistDataSource.addMovie(it)
        }
    }

    override suspend fun addTvSeries(id: Int, posterUrl: String, title: String) {
        WatchlistTvShowEntity(id = id, posterUrl = posterUrl, title = title).also {
            watchlistDataSource.addTvSeries(it)
        }
    }

    override suspend fun deleteMovie(id: Int) {
        watchlistDataSource.deleteMovie(id)
    }

    override suspend fun deleteTvSeries(id: Int) {
        watchlistDataSource.deleteTvSeries(id)
    }

}