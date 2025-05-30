package id.ak.movieshighlight.data.local

import id.ak.movieshighlight.data.local.room.WatchlistDatabase
import id.ak.movieshighlight.data.model.local.WatchlistMovieEntity
import id.ak.movieshighlight.data.model.local.WatchlistTvShowEntity
import kotlinx.coroutines.flow.map

class WatchlistDataSource(private val watchlistDatabase: WatchlistDatabase) {
    private val watchlistDao by lazy { watchlistDatabase.watchlistDao() }

    val movies = watchlistDao.getMovies()
    val tvSeries = watchlistDao.getTvShows()

    fun isMovieInWatchlist(id: Int) = watchlistDao.getMovieById(id).map { it != null }

    fun isTvShowInWatchlist(id: Int) = watchlistDao.getTvShowById(id).map { it != null }

    suspend fun addMovie(movieEntity: WatchlistMovieEntity) {
        watchlistDao.addMovie(movieEntity)
    }

    suspend fun addTvSeries(tvShowEntity: WatchlistTvShowEntity) {
        watchlistDao.addTvShow(tvShowEntity)
    }

    suspend fun deleteMovie(id: Int) {
        watchlistDao.removeMovie(id)
    }

    suspend fun deleteTvSeries(id: Int) {
        watchlistDao.removeTvShow(id)
    }
}