package id.ak.movieshighlight.service

import androidx.room.Room
import androidx.room.RoomDatabase
import id.ak.movieshighlight.data.local.room.WatchlistDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

private fun getDatabaseBuilder(): RoomDatabase.Builder<WatchlistDatabase> {
    val dbFilePath = getDocumentDirectory() + "/${WatchlistDatabase.DB_NAME}"
    return Room.databaseBuilder<WatchlistDatabase>(
        name = dbFilePath,
    )
}

@OptIn(ExperimentalForeignApi::class)
private fun getDocumentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}

actual fun createWatchlistDatabase(): WatchlistDatabase {
    val builder = getDatabaseBuilder()
    return getRoomDatabase(builder)
}