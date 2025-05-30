package id.ak.movieshighlight.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import id.ak.movieshighlight.data.model.local.WatchlistMovieEntity
import id.ak.movieshighlight.data.model.local.WatchlistTvShowEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchlistDao {
    @Query("SELECT * FROM WatchlistMovieEntity")
    fun getMovies(): Flow<List<WatchlistMovieEntity>>

    @Query("SELECT * FROM WatchlistTvShowEntity")
    fun getTvShows(): Flow<List<WatchlistTvShowEntity>>

    @Query("SELECT * FROM WatchlistMovieEntity WHERE id = :id")
    fun getMovieById(id: Int): Flow<WatchlistMovieEntity?>

    @Query("SELECT * FROM WatchlistTvShowEntity WHERE id = :id")
    fun getTvShowById(id: Int): Flow<WatchlistTvShowEntity?>

    @Insert
    suspend fun addMovie(movieEntity: WatchlistMovieEntity)

    @Insert
    suspend fun addTvShow(tvShowEntity: WatchlistTvShowEntity)

    @Query("DELETE FROM WatchlistMovieEntity WHERE id = :id")
    suspend fun removeMovie(id: Int)

    @Query("DELETE FROM WatchlistTvShowEntity WHERE id = :id")
    suspend fun removeTvShow(id: Int)
}