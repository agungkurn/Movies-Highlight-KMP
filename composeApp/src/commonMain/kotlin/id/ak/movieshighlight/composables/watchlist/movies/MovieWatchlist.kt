package id.ak.movieshighlight.composables.watchlist.movies

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import id.ak.movieshighlight.composables.FilmItem
import id.ak.movieshighlight.data.model.local.WatchlistMovieEntity

@Composable
fun MovieWatchlist(
    movies: List<WatchlistMovieEntity>,
    openDetails: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(modifier = modifier, columns = GridCells.Fixed(2)) {
        items(movies.size) { i ->
            val movie = movies[i]

            FilmItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f / 3f)
                    .clickable(onClick = { openDetails(movie.id) }),
                posterUrl = movie.posterUrl,
                title = movie.title
            )
        }
    }
}