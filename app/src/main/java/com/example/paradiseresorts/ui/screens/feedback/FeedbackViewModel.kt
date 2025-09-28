//Este archivo contiene la definición del ViewModel respectivo de FeedbackScreen
package com.example.paradiseresorts.ui.screens.feedback

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paradiseresorts.ui.classes.FeedbackUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FeedbackViewModel(/*Introducir repositorios necesarios*/) : ViewModel() {

    //Etiqueta de filtrado de logs referentes a este VM:
    companion object {
        private const val TAG = "feedbackVM"
    }

    var uiState: FeedbackUiState by mutableStateOf(FeedbackUiState())
        private set

    //Función de ejemplo para refrescar la pantalla de Feedback
    fun refreshFeedback() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            delay(1000) // Simulación de carga
            uiState = uiState.copy(
                isLoading = false,
                feedbackMessage = "Feedback actualizado"
            )
            Log.d(TAG, "Feedback actualizado")
        }
    }
}