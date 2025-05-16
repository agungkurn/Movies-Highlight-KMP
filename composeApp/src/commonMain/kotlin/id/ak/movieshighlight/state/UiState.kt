package id.ak.movieshighlight.state

sealed interface UiState<out T : Any> {
    data object Idle : UiState<Nothing>
    data object Loading : UiState<Nothing>
    data class Success<out R : Any>(val data: R) : UiState<R>
    data class Error(val message: String?) : UiState<Nothing>
}