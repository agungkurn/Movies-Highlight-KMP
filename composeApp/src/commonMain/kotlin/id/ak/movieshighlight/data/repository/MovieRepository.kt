package id.ak.movieshighlight.data.repository

import id.ak.movieshighlight.data.model.DiscoverMovieModel
import id.ak.movieshighlight.data.model.DiscoverTvModel
import id.ak.movieshighlight.data.remote.FilmRemoteSource

interface MovieRepository {
    suspend fun getMovies(): Result<List<DiscoverMovieModel>>
    suspend fun getTvSeries(): Result<List<DiscoverTvModel>>
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
}