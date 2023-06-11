package ru.apmgor.presentation.common.ui

sealed interface UiState<out T>
object NoContent : UiState<Nothing>
object Loading : UiState<Nothing>
data class Content<out R>(val value: R) : UiState<R>
data class Error(val error: Throwable) : UiState<Nothing>