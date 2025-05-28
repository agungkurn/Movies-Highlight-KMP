package id.ak.movieshighlight.data.repository

import id.ak.movieshighlight.data.local.ThemeLocalSource
import id.ak.movieshighlight.data.model.local.ThemeMode
import kotlinx.coroutines.flow.Flow

interface ThemeRepository {
    val themes: List<ThemeMode>
    val currentTheme: Flow<ThemeMode>

    suspend fun toggleTheme()
}

class ThemeRepositoryImpl(private val themeLocalSource: ThemeLocalSource) : ThemeRepository {
    override val themes = themeLocalSource.themes

    override val currentTheme = themeLocalSource.currentTheme

    override suspend fun toggleTheme() {
        themeLocalSource.toggleTheme()
    }
}