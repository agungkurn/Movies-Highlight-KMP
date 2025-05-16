package id.ak.movieshighlight.di

import id.ak.movieshighlight.BuildKonfig
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.parameters
import io.ktor.http.path
import io.ktor.http.set
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import kotlin.time.Duration.Companion.minutes

private const val BASE_URL = "api.themoviedb.org"

val serviceModule = module {
    single<HttpClient> {
        HttpClient {
            // throws error on non-2xx code
            expectSuccess = true

            // json configurations
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }

            // install logger
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.HEADERS
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
                    parameters.append("api_key", BuildKonfig.API_KEY)
                }
            }
        }
    }
}
