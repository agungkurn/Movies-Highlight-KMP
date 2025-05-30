package id.ak.movieshighlight

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import id.ak.movieshighlight.service.getDatabaseBuilder
import id.ak.movieshighlight.service.getPreferencesDataStorePath

class MainActivity : ComponentActivity() {
    private val roomBuilder by lazy {
        getDatabaseBuilder(applicationContext)
    }
    private val dataStorePath by lazy {
        getPreferencesDataStorePath(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            App(dataStorePath = dataStorePath, databaseBuilder = roomBuilder)
        }
    }
}