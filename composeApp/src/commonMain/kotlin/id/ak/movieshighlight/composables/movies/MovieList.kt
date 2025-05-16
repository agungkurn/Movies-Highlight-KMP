package id.ak.movieshighlight.composables.movies

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
import id.ak.movieshighlight.composables.FilmItem
import id.ak.movieshighlight.data.model.DiscoverMovieModel
import id.ak.movieshighlight.state.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieList(
    moviesState: UiState<List<DiscoverMovieModel>>,
    onRefresh: () -> Unit,
    openDetails: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        onRefresh()
    }

    PullToRefreshBox(
        modifier = modifier,
        isRefreshing = moviesState == UiState.Loading,
        onRefresh = onRefresh
    ) {
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            (moviesState as? UiState.Success<List<DiscoverMovieModel>>)?.data?.let { movies ->
                items(movies.size) { i ->
                    val movie = movies[i]

                    FilmItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(2f / 3f)
                            .clickable(onClick = { movie.id?.let { openDetails(it) } }),
                        posterUrl = movie.fullPosterPath,
                        title = movie.title ?: movie.originalTitle.orEmpty()
                    )
                }
            }
        }
    }
}