package id.ak.movieshighlight

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import id.ak.movieshighlight.service.getDatabaseBuilder

fun MainViewController() = ComposeUIViewController {
    val databaseBuilder = remember { getDatabaseBuilder() }

    App(databaseBuilder = databaseBuilder)
}