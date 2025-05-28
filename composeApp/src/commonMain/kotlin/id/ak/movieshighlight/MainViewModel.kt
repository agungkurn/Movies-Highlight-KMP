package id.ak.movieshighlight

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.ak.movieshighlight.data.model.local.ThemeMode
import id.ak.movieshighlight.data.repository.ThemeRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class MainViewModel(themeRepository: ThemeRepository) : ViewModel() {
    val currentTheme = themeRepository.currentTheme
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ThemeMode.System)
}