package id.ak.movieshighlight.service

import androidx.compose.runtime.Composable
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

internal const val dataStoreFileName = "theme.preferences_pb"

fun createDataStore(producePath: () -> String) = PreferenceDataStoreFactory.createWithPath {
    producePath().toPath()
}

@Composable
expect fun rememberPreferencesDataStore(): DataStore<Preferences>