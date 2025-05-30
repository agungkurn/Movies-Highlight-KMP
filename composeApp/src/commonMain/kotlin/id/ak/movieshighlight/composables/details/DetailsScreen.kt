package id.ak.movieshighlight.composables.details

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.PlaylistAdd
import androidx.compose.material.icons.automirrored.filled.PlaylistAddCheck
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import id.ak.movieshighlight.data.model.remote.MovieDetailsResponse
import id.ak.movieshighlight.data.model.remote.TvDetailsResponse
import id.ak.movieshighlight.ext.formatCurrency
import id.ak.movieshighlight.ext.formatLocalNumber
import id.ak.movieshighlight.state.UiState
import kotlinx.coroutines.launch
import movieshighlight.composeapp.generated.resources.Res
import movieshighlight.composeapp.generated.resources.baseline_18_up_rating_24
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    id: Int,
    isMovie: Boolean,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = koinViewModel()
) {
    val coroutineScope = rememberCoroutineScope()

    val movieState by viewModel.movieState.collectAsState()
    val tvSeriesState by viewModel.tvSeriesState.collectAsState()
    val isInWatchlist by viewModel.isInWatchlist.collectAsState()

    val filmId by remember {
        derivedStateOf {
            if (isMovie) {
                (movieState as? UiState.Success<MovieDetailsResponse>)?.data?.id
            } else {
                (tvSeriesState as? UiState.Success<TvDetailsResponse>)?.data?.id
            }
        }
    }
    val posterUrl by remember {
        derivedStateOf {
            if (isMovie) {
                (movieState as? UiState.Success<MovieDetailsResponse>)?.data?.fullPosterPath
            } else {
                (tvSeriesState as? UiState.Success<TvDetailsResponse>)?.data?.fullPosterPath
            }
        }
    }
    val title by remember {
        derivedStateOf {
            if (isMovie) {
                (movieState as? UiState.Success<MovieDetailsResponse>)?.data?.let {
                    it.title ?: it.originalTitle.orEmpty()
                }
            } else {
                (tvSeriesState as? UiState.Success<TvDetailsResponse>)?.data?.let {
                    it.name ?: it.originalName.orEmpty()
                }
            }
        }
    }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(id, isMovie) {
        viewModel.fetchDetails(id, isMovie)
    }

    LaunchedEffect(movieState, tvSeriesState) {
        (movieState as? UiState.Error)?.message?.let {
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar(it)
        }

        (tvSeriesState as? UiState.Error)?.message?.let {
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar(it)
        }
    }

    fun onWatchlistChanged(added: Boolean) {
        snackbarHostState.currentSnackbarData?.dismiss()

        coroutineScope.launch {
            if (added) {
                snackbarHostState.showSnackbar("Added to watchlist")
            } else {
                snackbarHostState.showSnackbar("Removed from watchlist")
            }
        }
    }

    BottomSheetScaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "back")
                    }
                },
                actions = {
                    AnimatedContent(targetState = isInWatchlist) { inWatchlist ->
                        filmId?.let { id ->
                            when (inWatchlist) {
                                true -> {
                                    FilledTonalIconButton(
                                        onClick = {
                                            viewModel.removeFromWatchlist(id)
                                            onWatchlistChanged(false)
                                        }
                                    ) {
                                        Icon(
                                            Icons.AutoMirrored.Filled.PlaylistAddCheck,
                                            contentDescription = "remove from watchlist"
                                        )
                                    }
                                }

                                false -> {
                                    IconButton(
                                        onClick = {
                                            viewModel.addToWatchlist(
                                                id = id,
                                                posterUrl = posterUrl.orEmpty(),
                                                title = title.orEmpty()
                                            )
                                            onWatchlistChanged(true)
                                        }
                                    ) {
                                        Icon(
                                            Icons.AutoMirrored.Filled.PlaylistAdd,
                                            contentDescription = "add to watchlist"
                                        )
                                    }
                                }

                                null -> {}
                            }
                        }
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        sheetContent = {
            Box(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                    .navigationBarsPadding()
            ) {
                (movieState as? UiState.Success)?.data?.let { movie ->
                    MovieDetails(movie = movie, modifier = Modifier.fillMaxWidth())
                }

                (tvSeriesState as? UiState.Success)?.data?.let { tvSeries ->
                    TvSeriesDetails(
                        modifier = Modifier.fillMaxWidth(),
                        tvSerial = tvSeries
                    )
                }
            }
        },
        sheetPeekHeight = 100.dp + WindowInsets.navigationBars.asPaddingValues()
            .calculateBottomPadding()
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .consumeWindowInsets(it)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (movieState == UiState.Loading || tvSeriesState == UiState.Loading) {
                CircularProgressIndicator()
            }

            (movieState as? UiState.Success)?.data?.let { movie ->
                AsyncImage(
                    modifier = Modifier.fillMaxWidth(),
                    model = posterUrl,
                    contentDescription = movie.title ?: movie.originalTitle,
                    contentScale = ContentScale.FillWidth
                )
            }

            (tvSeriesState as? UiState.Success)?.data?.let { tvSeries ->
                AsyncImage(
                    modifier = Modifier.fillMaxWidth(),
                    model = posterUrl,
                    contentDescription = tvSeries.name ?: tvSeries.originalName,
                    contentScale = ContentScale.FillWidth
                )
            }
        }
    }
}

@Composable
private fun MovieDetails(
    movie: MovieDetailsResponse,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = movie.title.orEmpty(), style = MaterialTheme.typography.titleLarge)
                Text(text = movie.tagline.orEmpty(), style = MaterialTheme.typography.titleSmall)
                if (movie.adult == true) {
                    Icon(
                        painterResource(Res.drawable.baseline_18_up_rating_24),
                        contentDescription = "adult movie"
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    Icons.Default.Star,
                    contentDescription = "rating: ${movie.voteAverage} of ${movie.voteCount} votes"
                )
                Text(text = "${movie.voteAverage?.formatLocalNumber()} (${movie.voteCount?.formatLocalNumber()})")
            }
        }
        HorizontalDivider()
        Text(text = movie.overview.orEmpty())
        Text(text = "Genre: ${movie.genresDisplay}")
        Text(text = "Runtime: ${movie.runtime} minutes")
        Text(text = "Status: ${movie.status}")
        Text(text = "Release date: ${movie.releaseDate}")
        Text(text = "Original language: ${movie.originalLanguage?.uppercase()}")
        Text(text = "Budget: ${movie.budget?.formatCurrency()}")
        Text(text = "Revenue: ${movie.revenue?.formatCurrency()}")
        Text(text = "Origin country: ${movie.originCountriesDisplay}")
        Text(text = "Production companies: ${movie.productionCompaniesDisplay}")
        Text(text = "Production countries: ${movie.originCountriesDisplay}")
        movie.belongsToCollection?.let {
            Text(text = "Collection: $it")
        }
    }
}

@Composable
private fun TvSeriesDetails(
    tvSerial: TvDetailsResponse,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = tvSerial.name ?: tvSerial.originalName.orEmpty(),
                    style = MaterialTheme.typography.titleLarge
                )
                tvSerial.tagline?.let {
                    Text(text = it, style = MaterialTheme.typography.titleSmall)
                }
                if (tvSerial.adult == true) {
                    Icon(
                        painterResource(Res.drawable.baseline_18_up_rating_24),
                        contentDescription = "adult show"
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    Icons.Default.Star,
                    contentDescription = "rating: ${tvSerial.voteAverage} of ${tvSerial.voteCount} votes"
                )
                Text(text = "${tvSerial.voteAverage?.formatLocalNumber()} (${tvSerial.voteCount?.formatLocalNumber()})")
            }
        }
        HorizontalDivider()
        if (tvSerial.inProduction == true) {
            Text(text = "Still in production")
        }
        Text(text = tvSerial.overview.orEmpty())
        Text(text = "Genre: ${tvSerial.genresDisplay}")
        Text(text = "Runtime: ${tvSerial.runtimeDisplay} minutes")
        Text(text = "Status: ${tvSerial.status}")
        Text(text = "Original language: ${tvSerial.originalLanguage?.uppercase()}")
        Text(text = "Origin country: ${tvSerial.originCountriesDisplay}")
        Text(text = "Production companies: ${tvSerial.productionCompaniesDisplay}")
        Text(text = "Production countries: ${tvSerial.productionCountriesDisplay}")
        Text(text = "Networks: ${tvSerial.networkDisplay}")
    }
}