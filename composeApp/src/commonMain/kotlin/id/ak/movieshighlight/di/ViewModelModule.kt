package id.ak.movieshighlight.di

import id.ak.movieshighlight.composables.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::HomeViewModel)
}