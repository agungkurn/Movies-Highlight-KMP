package id.ak.movieshighlight.di

import id.ak.movieshighlight.data.remote.FilmRemoteSource
import org.koin.dsl.module

val dataSourceModule = module {
    single { FilmRemoteSource(get()) }
}