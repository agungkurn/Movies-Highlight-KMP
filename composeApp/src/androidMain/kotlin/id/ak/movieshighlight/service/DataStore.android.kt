package id.ak.movieshighlight.service

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

@Composable
actual fun rememberPreferencesDataStore(): DataStore<Preferences> {
    val context = LocalContext.current

    return remember {
        createDataStore {
            context.filesDir.resolve(dataStoreFileName).absolutePath
        }
    }
}