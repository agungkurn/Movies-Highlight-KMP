package id.ak.movieshighlight.service

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import okio.Path.Companion.toPath

internal const val dataStoreFileName = "theme.preferences_pb"

@Composable
fun rememberPreferencesDataStore(path: String) = remember {
    PreferenceDataStoreFactory.createWithPath {
        path.toPath()
    }
}