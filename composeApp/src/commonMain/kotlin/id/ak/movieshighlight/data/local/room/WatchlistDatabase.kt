package id.ak.movieshighlight.data.local.room

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import id.ak.movieshighlight.data.model.local.WatchlistMovieEntity
import id.ak.movieshighlight.data.model.local.WatchlistTvShowEntity

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object WatchlistDatabaseConstructor : RoomDatabaseConstructor<WatchlistDatabase> {
    override fun initialize(): WatchlistDatabase
}

@Database(
    entities = [WatchlistMovieEntity::class, WatchlistTvShowEntity::class],
    version = 1
)
@ConstructedBy(WatchlistDatabaseConstructor::class)
abstract class WatchlistDatabase : RoomDatabase() {
    abstract fun watchlistDao(): WatchlistDao
}
