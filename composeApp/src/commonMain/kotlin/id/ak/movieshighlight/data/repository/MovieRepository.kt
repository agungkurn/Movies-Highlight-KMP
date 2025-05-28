package id.ak.movieshighlight.data.repository

import id.ak.movieshighlight.data.model.remote.DiscoverMovieModel
import id.ak.movieshighlight.data.model.remote.DiscoverTvModel
import id.ak.movieshighlight.data.model.remote.MovieDetailsResponse
import id.ak.movieshighlight.data.model.remote.TvDetailsResponse
import id.ak.movieshighlight.data.remote.FilmRemoteSource

interface MovieRepository {
    suspend fun getMovies(): Result<List<DiscoverMovieModel>>
    suspend fun getTvSeries(): Result<List<DiscoverTvModel>>
    suspend fun getMovieDetails(id: Int): Result<MovieDetailsResponse>
    suspend fun getTvSeriesDetails(id: Int): Result<TvDetailsResponse>
}

class MovieRepositoryImpl(private val filmRemoteSource: FilmRemoteSource) : MovieRepository {
    override suspend fun getMovies(): Result<List<DiscoverMovieModel>> {
        return runCatching {
            filmRemoteSource.getMovies().results.orEmpty()
        }
    }

    override suspend fun getTvSeries(): Result<List<DiscoverTvModel>> {
        return runCatching {
            filmRemoteSource.getTvSeries().results.orEmpty()
        }
    }

    override suspend fun getMovieDetails(id: Int): Result<MovieDetailsResponse> {
        return runCatching {
            filmRemoteSource.getMovieDetails(id)
        }
    }

    override suspend fun getTvSeriesDetails(id: Int): Result<TvDetailsResponse> {
        return runCatching {
            filmRemoteSource.getTvSeriesDetails(id)
        }
    }
}