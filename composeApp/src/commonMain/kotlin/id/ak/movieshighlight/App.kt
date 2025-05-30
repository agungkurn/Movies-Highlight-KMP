package id.ak.movieshighlight

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.room.RoomDatabase
import id.ak.movieshighlight.composables.details.DetailsScreen
import id.ak.movieshighlight.composables.home.HomeScreen
import id.ak.movieshighlight.composables.watchlist.WatchlistScreen
import id.ak.movieshighlight.data.local.room.WatchlistDatabase
import id.ak.movieshighlight.data.model.local.ThemeMode
import id.ak.movieshighlight.di.dataSourceModule
import id.ak.movieshighlight.di.remoteServiceModule
import id.ak.movieshighlight.di.repositoryModule
import id.ak.movieshighlight.di.viewModelModule
import id.ak.movieshighlight.service.rememberPreferencesDataStore
import id.ak.movieshighlight.service.rememberWatchlistDatabase
import id.ak.movieshighlight.ui.AdjustSystemBar
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel
import org.koin.dsl.module

@Composable
fun AppTheme(colorScheme: ColorScheme, content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}

@Composable
fun App(databaseBuilder: RoomDatabase.Builder<WatchlistDatabase>) {
    val dataStore = rememberPreferencesDataStore()
    val database = rememberWatchlistDatabase(databaseBuilder)

    val platformModule = remember(dataStore) {
        module {
            single { dataStore }
            single { database }
        }
    }

    KoinApplication(
        application = {
            modules(
                viewModelModule,
                repositoryModule,
                dataSourceModule,
                remoteServiceModule,
                platformModule
            )
        }
    ) {
        val viewModel = koinViewModel<MainViewModel>()
        val currentTheme by viewModel.currentTheme.collectAsState()

        AdjustSystemBar(
            isDark = when (currentTheme) {
                ThemeMode.Dark -> true
                ThemeMode.Light -> false
                ThemeMode.System -> isSystemInDarkTheme()
            }
        )

        AppTheme(
            colorScheme = currentTheme.colorScheme
                ?: if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
        ) {
            val navController = rememberNavController()

            NavHost(navController, MainRoute.Home) {
                composable<MainRoute.Home> {
                    HomeScreen(
                        openMovieDetails = {
                            val route = MainRoute.Details(id = it, isMovie = true)
                            navController.navigate(route)
                        },
                        openTvSerialDetails = {
                            val route = MainRoute.Details(id = it, isMovie = false)
                            navController.navigate(route)
                        },
                        openWatchlist = {
                            navController.navigate(MainRoute.Watchlist)
                        }
                    )
                }
                composable<MainRoute.Details> {
                    val details = it.toRoute<MainRoute.Details>()

                    DetailsScreen(
                        id = details.id,
                        isMovie = details.isMovie,
                        onNavigateUp = { navController.popBackStack() }
                    )
                }
                composable<MainRoute.Watchlist> {
                    WatchlistScreen(
                        onNavigateUp = { navController.popBackStack() },
                        openMovieDetails = {
                            val route = MainRoute.Details(id = it, isMovie = true)
                            navController.navigate(route)
                        },
                        openTvSerialDetails = {
                            val route = MainRoute.Details(id = it, isMovie = false)
                            navController.navigate(route)
                        }
                    )
                }
            }
        }
    }
}