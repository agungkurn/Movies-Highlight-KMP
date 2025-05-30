package id.ak.movieshighlight

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import id.ak.movieshighlight.service.getDatabaseBuilder
import id.ak.movieshighlight.service.getPreferencesDataStorePath

fun MainViewController() = ComposeUIViewController {
    val databaseBuilder = remember { getDatabaseBuilder() }
    val dataStorePath = remember { getPreferencesDataStorePath() }

    App(dataStorePath = dataStorePath, databaseBuilder = databaseBuilder)
}