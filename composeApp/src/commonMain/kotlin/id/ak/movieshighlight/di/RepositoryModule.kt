package id.ak.movieshighlight.di

import id.ak.movieshighlight.data.repository.MovieRepository
import id.ak.movieshighlight.data.repository.MovieRepositoryImpl
import id.ak.movieshighlight.data.repository.ThemeRepository
import id.ak.movieshighlight.data.repository.ThemeRepositoryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::MovieRepositoryImpl) { bind<MovieRepository>() }
    singleOf(::ThemeRepositoryImpl) { bind<ThemeRepository>() }
}