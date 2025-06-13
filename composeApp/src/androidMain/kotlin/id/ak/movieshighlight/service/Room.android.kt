package id.ak.movieshighlight.service

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import id.ak.movieshighlight.data.local.room.WatchlistDatabase

private lateinit var applicationContext: Context

fun initDatabase(appContext: Context) {
    if (::applicationContext.isInitialized) return
    applicationContext = appContext
}

private fun getDatabaseBuilder(appContext: Context): RoomDatabase.Builder<WatchlistDatabase> {
    val dbFile = appContext.getDatabasePath(WatchlistDatabase.DB_NAME)
    return Room.databaseBuilder<WatchlistDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}

actual fun createWatchlistDatabase(): WatchlistDatabase {
    val builder = getDatabaseBuilder(applicationContext)
    return getRoomDatabase(builder)
}