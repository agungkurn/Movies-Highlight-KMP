package id.ak.movieshighlight

import kotlinx.serialization.Serializable

@Serializable
sealed class MainRoute {
    @Serializable
    data object Home : MainRoute()

    @Serializable
    data class Details(val id: Int, val isMovie: Boolean) : MainRoute()

    @Serializable
    data object Watchlist : MainRoute()
}