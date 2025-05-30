package id.ak.movieshighlight.composables.watchlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import id.ak.movieshighlight.composables.watchlist.movies.MovieWatchlist
import id.ak.movieshighlight.composables.watchlist.tv.TvSeriesWatchlist
import kotlinx.coroutines.launch
import movieshighlight.composeapp.generated.resources.Res
import movieshighlight.composeapp.generated.resources.list_tabs
import org.jetbrains.compose.resources.stringArrayResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchlistScreen(
    onNavigateUp: () -> Unit,
    openMovieDetails: (Int) -> Unit,
    openTvSerialDetails: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WatchlistViewModel = koinViewModel()
) {
    val movies by viewModel.movies.collectAsState()
    val tvSeries by viewModel.tvSeries.collectAsState()

    val tabs = stringArrayResource(Res.array.list_tabs)
    val pagerState = rememberPagerState { tabs.size }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Watchlist") },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "back")
                    }
                }
            )
        },
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .consumeWindowInsets(it)
        ) {
            PrimaryTabRow(selectedTabIndex = pagerState.currentPage) {
                tabs.forEachIndexed { i, title ->
                    Tab(
                        selected = i == pagerState.currentPage,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(i)
                            }
                        },
                        text = { Text(text = title) }
                    )
                }
            }
            HorizontalPager(state = pagerState) { i ->
                when (i) {
                    0 -> {
                        MovieWatchlist(
                            modifier = Modifier.fillMaxSize(),
                            movies = movies,
                            openDetails = openMovieDetails
                        )
                    }

                    1 -> {
                        TvSeriesWatchlist(
                            modifier = Modifier.fillMaxSize(),
                            tvSeries = tvSeries,
                            openDetails = openTvSerialDetails
                        )
                    }
                }
            }
        }
    }
}