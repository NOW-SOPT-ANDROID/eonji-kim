package com.sopt.now.core.util.view

sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>

    data class Success<T>(
        val data: T
    ) : UiState<T>

    data class Failure(
        val errorMessage: String
    ) : UiState<Nothing>
}