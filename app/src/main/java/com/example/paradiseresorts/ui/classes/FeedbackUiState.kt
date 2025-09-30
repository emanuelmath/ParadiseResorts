//Este archivo contiene los observables desde la UI de feedback.
package com.example.paradiseresorts.ui.classes

import com.example.paradiseresorts.domain.models.Feedback

data class FeedbackUiState(
    val feedbacks: List<Feedback> = emptyList(), // ← añadir esto
    val isLoading: Boolean = false,
    val feedbackMessage: String = "Bienvenido a la pantalla de Feedback",
    val errorMessage: String? = null
)