package id.ak.movieshighlight.di

import id.ak.movieshighlight.MainViewModel
import id.ak.movieshighlight.composables.details.DetailsViewModel
import id.ak.movieshighlight.composables.home.HomeViewModel
import id.ak.movieshighlight.composables.watchlist.WatchlistViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::DetailsViewModel)
    viewModelOf(::WatchlistViewModel)
}