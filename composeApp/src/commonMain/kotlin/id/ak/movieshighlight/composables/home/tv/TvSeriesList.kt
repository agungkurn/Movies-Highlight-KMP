package id.ak.movieshighlight.composables.home.tv

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import id.ak.movieshighlight.composables.home.FilmItem
import id.ak.movieshighlight.data.model.remote.DiscoverTvModel
import id.ak.movieshighlight.state.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TvSeriesList(
    tvSeriesState: UiState<List<DiscoverTvModel>>,
    onRefresh: () -> Unit,
    openDetails: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        onRefresh()
    }

    PullToRefreshBox(
        modifier = modifier,
        isRefreshing = tvSeriesState == UiState.Loading,
        onRefresh = onRefresh
    ) {
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            (tvSeriesState as? UiState.Success<List<DiscoverTvModel>>)?.data?.let { tvSeries ->
                items(tvSeries.size) { i ->
                    val tvModel = tvSeries[i]

                    FilmItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(2f / 3f)
                            .clickable(onClick = { tvModel.id?.let { openDetails(it) } }),
                        posterUrl = tvModel.fullPosterPath,
                        title = tvModel.name ?: tvModel.originalName.orEmpty()
                    )
                }
            }
        }
    }
}