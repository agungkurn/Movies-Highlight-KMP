package id.ak.movieshighlight.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import id.ak.movieshighlight.data.model.local.ThemeMode
import kotlinx.coroutines.flow.map

class ThemeLocalSource(private val dataStore: DataStore<Preferences>) {
    private val themeKey = intPreferencesKey("theme")

    val themes = listOf(ThemeMode.System, ThemeMode.Dark, ThemeMode.Light)

    val currentTheme = dataStore.data.map { pref ->
        pref[themeKey]?.let { id ->
            themes.firstOrNull { it.id == id }
        } ?: themes.first()
    }

    suspend fun toggleTheme() {
        dataStore.edit { pref ->
            pref[themeKey] = when (pref[themeKey]) {
                ThemeMode.System.id -> ThemeMode.Dark.id
                ThemeMode.Dark.id -> ThemeMode.Light.id
                ThemeMode.Light.id -> ThemeMode.System.id
                else -> ThemeMode.Dark.id
            }
        }
    }
}