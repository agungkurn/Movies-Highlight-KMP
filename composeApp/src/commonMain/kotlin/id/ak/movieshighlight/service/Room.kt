package id.ak.movieshighlight.service

import androidx.room.RoomDatabase
import id.ak.movieshighlight.data.local.room.WatchlistDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

fun getRoomDatabase(
    builder: RoomDatabase.Builder<WatchlistDatabase>
): WatchlistDatabase = builder
    .setQueryCoroutineContext(Dispatchers.IO)
    .build()

expect fun createWatchlistDatabase(): WatchlistDatabase
