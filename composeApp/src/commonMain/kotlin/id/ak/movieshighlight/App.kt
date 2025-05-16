package id.ak.movieshighlight

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import id.ak.movieshighlight.composables.HomeScreen
import id.ak.movieshighlight.di.dataSourceModule
import id.ak.movieshighlight.di.repositoryModule
import id.ak.movieshighlight.di.serviceModule
import id.ak.movieshighlight.di.viewModelModule
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme(),
        content = content
    )
}

@Composable
@Preview
fun App() {
    KoinApplication(
        application = {
            modules(
                viewModelModule,
                repositoryModule,
                dataSourceModule,
                serviceModule
            )
        }
    ) {
        AppTheme {
            val navController = rememberNavController()

            NavHost(navController, MainRoute.Home) {
                composable<MainRoute.Home> {
                    HomeScreen(
                        openMovieDetails = {},
                        openTvSerialDetails = {}
                    )
                }
            }
        }
    }
}