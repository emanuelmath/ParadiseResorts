//Este archivo contiene los observables desde la UI de feedback.
package com.example.paradiseresorts.ui.classes

data class FeedbackUiState(
    val isLoading: Boolean = false,
    val feedbackMessage: String = "Bienvenido a la pantalla de Feedback",
    val errorMessage: String? = null
)