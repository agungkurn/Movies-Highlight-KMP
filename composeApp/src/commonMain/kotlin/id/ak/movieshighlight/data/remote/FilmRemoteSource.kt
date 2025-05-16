package id.ak.movieshighlight.data.remote

import id.ak.movieshighlight.data.model.DiscoverMovieResponse
import id.ak.movieshighlight.data.model.DiscoverTvResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class FilmRemoteSource(private val httpClient: HttpClient) {
    suspend fun getMovies(): DiscoverMovieResponse {
        val request = httpClient.get("discover/movie")
        val response = request.body<DiscoverMovieResponse>()
        return response
    }

    suspend fun getTvSeries(): DiscoverTvResponse {
        val request = httpClient.get("discover/tv")
        val response = request.body<DiscoverTvResponse>()
        return response
    }
}