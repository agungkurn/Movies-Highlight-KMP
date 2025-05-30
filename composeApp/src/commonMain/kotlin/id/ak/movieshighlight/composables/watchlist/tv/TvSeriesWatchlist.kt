package id.ak.movieshighlight.composables.watchlist.tv

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import id.ak.movieshighlight.composables.FilmItem
import id.ak.movieshighlight.data.model.local.WatchlistTvShowEntity

@Composable
fun TvSeriesWatchlist(
    tvSeries: List<WatchlistTvShowEntity>,
    openDetails: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(modifier = modifier, columns = GridCells.Fixed(2)) {
        items(tvSeries.size) { i ->
            val tvModel = tvSeries[i]

            FilmItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f / 3f)
                    .clickable(onClick = { openDetails(tvModel.id) }),
                posterUrl = tvModel.posterUrl,
                title = tvModel.title
            )
        }
    }
}