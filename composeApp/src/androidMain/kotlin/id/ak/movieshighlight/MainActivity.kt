package id.ak.movieshighlight

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import id.ak.movieshighlight.service.initDatabase
import id.ak.movieshighlight.service.initPreferencesDataStore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        initDatabase(applicationContext)
        initPreferencesDataStore(applicationContext)

        setContent {
            App()
        }
    }
}