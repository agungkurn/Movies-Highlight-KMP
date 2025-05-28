package id.ak.movieshighlight.data.model.local

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import movieshighlight.composeapp.generated.resources.Res
import movieshighlight.composeapp.generated.resources.theme_auto
import movieshighlight.composeapp.generated.resources.theme_dark
import movieshighlight.composeapp.generated.resources.theme_light
import org.jetbrains.compose.resources.StringResource

sealed class ThemeMode(
    val id: Int,
    val stringResource: StringResource,
    val colorScheme: ColorScheme?
) {
    data object System :
        ThemeMode(id = 0, stringResource = Res.string.theme_auto, colorScheme = null)

    data object Dark :
        ThemeMode(id = 1, stringResource = Res.string.theme_dark, colorScheme = darkColorScheme())

    data object Light :
        ThemeMode(id = 2, stringResource = Res.string.theme_light, colorScheme = lightColorScheme())
}