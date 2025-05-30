package id.ak.movieshighlight.service

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.room.RoomDatabase
import id.ak.movieshighlight.data.local.room.WatchlistDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Composable
fun rememberWatchlistDatabase(builder: RoomDatabase.Builder<WatchlistDatabase>) = remember {
    builder
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}