package id.ak.movieshighlight.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage

@Composable
fun FilmItem(posterUrl: String, title: String, modifier: Modifier = Modifier) {
    AsyncImage(
        modifier = modifier,
        model = posterUrl,
        contentDescription = title,
        contentScale = ContentScale.Crop
    )
}