package id.ak.movieshighlight.service

import android.content.Context

fun getPreferencesDataStorePath(appContext: Context): String =
    appContext.filesDir.resolve(dataStoreFileName).absolutePath