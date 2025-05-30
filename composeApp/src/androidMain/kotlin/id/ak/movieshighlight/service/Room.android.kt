package id.ak.movieshighlight.service

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import id.ak.movieshighlight.data.local.room.WatchlistDatabase

fun getDatabaseBuilder(appContext: Context): RoomDatabase.Builder<WatchlistDatabase> {
    val dbFile = appContext.getDatabasePath("watchlist.db")

    return Room.databaseBuilder<WatchlistDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}