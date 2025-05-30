package id.ak.movieshighlight.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WatchlistMovieEntity(
    @PrimaryKey val id: Int,
    val posterUrl: String,
    val title: String
)
