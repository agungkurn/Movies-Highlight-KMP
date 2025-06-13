package id.ak.movieshighlight.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import id.ak.movieshighlight.BuildKonfig
import id.ak.movieshighlight.data.local.room.WatchlistDatabase
import id.ak.movieshighlight.service.createPreferencesDataStore
import id.ak.movieshighlight.service.createWatchlistDatabase
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import kotlin.time.Duration.Companion.minutes

private const val BASE_URL = "api.themoviedb.org"

val localServiceModule = module {
    single<DataStore<Preferences>> { createPreferencesDataStore() }
    single<WatchlistDatabase> { createWatchlistDatabase() }
}

val remoteServiceModule = module {
    single<HttpClient> {
        HttpClient {
            // throws error on non-2xx code
            expectSuccess = true

            // json configurations
            install(ContentNegotiation) {
                json(
                    Json {
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }

            // install logger
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Napier.d(tag = "ktor") { message }
                    }
                }
                level = LogLevel.BODY
            }

            // timeout
            install(HttpTimeout) {
                requestTimeoutMillis = 2.minutes.inWholeMilliseconds
            }

            // set default request
            defaultRequest {
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)

                url {
                    protocol = URLProtocol.HTTPS
                    // set base url
                    host = BASE_URL
                    path("3/")
                }
            }

            // token
            install(Auth) {
                bearer {
                    loadTokens {
                        BearerTokens(accessToken = BuildKonfig.API_KEY, refreshToken = null)
                    }
                }
            }
        }.also { Napier.base(DebugAntilog()) }
    }
}