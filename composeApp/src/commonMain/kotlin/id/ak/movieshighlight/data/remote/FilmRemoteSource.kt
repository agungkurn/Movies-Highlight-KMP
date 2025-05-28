package id.ak.movieshighlight.data.remote

import id.ak.movieshighlight.data.model.remote.DiscoverMovieResponse
import id.ak.movieshighlight.data.model.remote.DiscoverTvResponse
import id.ak.movieshighlight.data.model.remote.MovieDetailsResponse
import id.ak.movieshighlight.data.model.remote.TvDetailsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class FilmRemoteSource(private val httpClient: HttpClient) {
    suspend fun getMovies(): DiscoverMovieResponse {
        val response = httpClient.get("discover/movie")
        val body = response.body<DiscoverMovieResponse>()
        return body
    }

    suspend fun getTvSeries(): DiscoverTvResponse {
        val response = httpClient.get("discover/tv")
        val body = response.body<DiscoverTvResponse>()
        return body
    }

    suspend fun getMovieDetails(id: Int): MovieDetailsResponse {
        val response = httpClient.get("movie/$id")
        val body = response.body<MovieDetailsResponse>()
        return body
    }

    suspend fun getTvSeriesDetails(id: Int): TvDetailsResponse {
        val response = httpClient.get("tv/$id")
        val body = response.body<TvDetailsResponse>()
        return body
    }
}