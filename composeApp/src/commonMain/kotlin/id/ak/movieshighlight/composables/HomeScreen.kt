package id.ak.movieshighlight.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import id.ak.movieshighlight.composables.movies.MovieList
import id.ak.movieshighlight.composables.tv.TvSeriesList
import id.ak.movieshighlight.state.UiState
import kotlinx.coroutines.launch
import movieshighlight.composeapp.generated.resources.Res
import movieshighlight.composeapp.generated.resources.list_tabs
import org.jetbrains.compose.resources.stringArrayResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    openMovieDetails: (Int) -> Unit,
    openTvSerialDetails: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel()
) {
    val moviesState by viewModel.movies.collectAsState()
    val tvSeriesState by viewModel.tvSeries.collectAsState()

    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val tabs = stringArrayResource(Res.array.list_tabs)
    val pagerState = rememberPagerState { tabs.size }

    LaunchedEffect(moviesState, tvSeriesState) {
        (moviesState as? UiState.Error)?.message?.let {
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar(it)
        }

        (tvSeriesState as? UiState.Error)?.message?.let {
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar(it)
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                scrollBehavior = topAppBarScrollBehavior,
                title = { Text("Movies Highlight") }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
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
                        MovieList(
                            modifier = Modifier.fillMaxSize(),
                            moviesState = moviesState,
                            onRefresh = viewModel::fetchMovies,
                            openDetails = openMovieDetails
                        )
                    }

                    1 -> {
                        TvSeriesList(
                            modifier = Modifier.fillMaxSize(),
                            tvSeriesState = tvSeriesState,
                            onRefresh = viewModel::fetchTvSeries,
                            openDetails = openTvSerialDetails
                        )
                    }
                }
            }
        }
    }
}